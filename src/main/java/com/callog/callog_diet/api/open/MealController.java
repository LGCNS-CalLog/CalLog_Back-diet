package com.callog.callog_diet.api.open;

import com.callog.callog_diet.domain.MealType;
import com.callog.callog_diet.domain.dto.meal.MealRequest;
import com.callog.callog_diet.service.MealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/diet/meal", produces = MediaType.APPLICATION_JSON_VALUE)
public class MealController {
    private final MealService mealService;

    // create: 식단 기록
    // TODO: 반환값 공통 코드화
    @PostMapping(value = "")
    public String createMeal(@RequestBody MealRequest.MealCreateRequest request) {

        return "";
    }

    // read: 특정 날짜 식단 기록 조회 (아침, 점심, 저녁 한번에)
    // TODO: 반환값 공통 코드화
    @GetMapping(value = "")
    public String readDateMealList(@RequestParam(required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date) {
        date = getOrToday(date);
        // 서비스 호출
        return "";
    }

    // read: 특정 날짜 특정 mealType 식단 조회 (음식 수정 화면에서 사용 예정)
    // TODO: 반환값 공통 코드화
    @GetMapping(value = "/type")
    public String readDateMealTypeList(@RequestParam(required = false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date,
                                      @RequestParam MealType mealType) {
        date = getOrToday(date);
        // 서비스 호출
        return "";
    }

    // update: 식단 기록 수정
    // TODO: 반환값 공통 코드화
    @PatchMapping(value = "")
    public String updateMeal(@RequestBody MealRequest.MealUpdateRequest request) {

        return "";
    }

    // delete: 식단 기록 삭제
    // TODO: 반환값 공통 코드화
    @DeleteMapping(value = "/{mealId}")
    public String deleteMeal(@PathVariable Long mealId) {
        return "";
    }

    // Date Null일 경우, 오늘 날짜 주입
    private LocalDate getOrToday(LocalDate date) {
        return (date != null) ? date : LocalDate.now();
    }

    // read: 식단 기록 전체 조회 (달력, 월단위) (보류)
}
