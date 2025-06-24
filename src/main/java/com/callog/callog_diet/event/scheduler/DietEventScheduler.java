package com.callog.callog_diet.event.scheduler;

import com.callog.callog_diet.domain.MealType;
import com.callog.callog_diet.domain.entity.Meal;
import com.callog.callog_diet.domain.event.DietCompleteEvent;
import com.callog.callog_diet.domain.repository.MealRepository;
import com.callog.callog_diet.event.domain.MealInfo;
import com.callog.callog_diet.event.producer.KafkaMessageProducer;
import com.callog.callog_diet.remote.user.RemoteUserService;
import com.callog.callog_diet.remote.user.dto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class DietEventScheduler {
    private final MealRepository mealRepository;
    private final RemoteUserService remoteUserService;
    private final KafkaMessageProducer kafkaMessageProducer;

    // 아침 식단 계획 이행 여부 확인 이메일
    @Scheduled(cron = "0 30 8 * * *")
    public void runBreakfastScheduler() {
        runMealNotificationByType(MealType.BREAKFAST);
    }

    // 점심 식단 계획 이행 여부 확인 이메일
    @Scheduled(cron = "0 30 11 * * *")
    public void runLunchScheduler() {
        runMealNotificationByType(MealType.LUNCH);
    }

    // 저녁 식단 계획 이행 여부 확인 이메일
    @Scheduled(cron = "0 30 17 * * *")
    public void runDinnerScheduler() {
        runMealNotificationByType(MealType.DINNER);
    }

    public void runMealNotificationByType(MealType mealType) {
        log.info("✅ {} 식단 알림 스케줄러 시작", mealTypeToKor(mealType));

        // Meal Table 조회 (이미 오늘 날짜, 특정 식사 타입 데이터가 존재하는 경우)
        List<Meal> meals = mealRepository.findByDateAndMealType(LocalDate.now(), mealType);

        // 존재하는 userId 중복 제거해서 추출
        Map<Long, List<Meal>> mealGroupedByUser = meals.stream()
                .collect(Collectors.groupingBy(Meal::getUserId));

        for (Map.Entry<Long, List<Meal>> entry : mealGroupedByUser.entrySet()) {
            Long userId = entry.getKey();
            List<Meal> userMeals = entry.getValue();

            try {
                // 1. 유저 정보 조회 (FeignClient 등으로)
                // user 별로 remote user getUserInfo() 메소드 remote 호출
                UserInfoDto.Response user = remoteUserService.getUserInfo(userId);

                // 2. 음식 정보 구성
                // 음식 정보 가져와서 message에 넣기
                List<MealInfo> foodList = userMeals.stream()
                        .map(meal -> new MealInfo(meal.getFoodName(), meal.getAmount()))
                        .collect(Collectors.toList());

                // 3. kafka 통해 보낼 email title, content 부분 생성
                String title = buildMealTitle(user.getNickname(), mealType);
                String content = buildMealContent(foodList, mealType);

                // 5. 메시지 생성
                DietCompleteEvent message = DietCompleteEvent.builder()
                        .action("ReadMealPlan")
                        .userId(user.getUsername())
                        .nickName(user.getNickname())
                        .subject(title)
                        .message(content)
                        .build();

                // 6. Kafka 전송
                kafkaMessageProducer.send(DietCompleteEvent.Topic, message);
            } catch (Exception e) {
                log.warn("❌ [{}] 사용자 정보 조회 또는 Kafka 전송 실패 (userId: {}) - {}", mealType, userId, e.getMessage());
            }
        }
    }



    public String buildMealTitle(String nickname, MealType mealType) {
        String type = mealTypeToKor(mealType);
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(nickname).append("님의 ").append(type).append(" 식사 계획>").append("<br/>");

        return sb.toString();
    }

    public String buildMealContent(List<MealInfo> foodList, MealType mealType) {
        StringBuilder sb = new StringBuilder();
        String type = mealTypeToKor(mealType);

        sb.append("[").append(LocalDate.now()).append(" ").append(type).append("]<br/>");

        for (MealInfo food : foodList) {
            sb.append(food.getFoodName())
                    .append(": ")
                    .append(food.getAmount())
                    .append("인분<br/>");
        }

        sb.append("<br/>위처럼 드셨나요?");
        return sb.toString();
    }


    // 식사 타입 한국어로 변경
    public String mealTypeToKor(MealType mealType) {
        return switch (mealType) {
            case BREAKFAST -> "아침";
            case LUNCH -> "점심";
            case DINNER -> "저녁";
        };
    }
}
