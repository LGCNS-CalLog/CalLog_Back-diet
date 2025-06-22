package com.callog.callog_diet.domain.dto.meal;

import com.callog.callog_diet.domain.MealType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class MealRequest {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MealCreateRequest {
        private Long userId;
        private LocalDate date;
        private MealType mealType;
        private Long foodId;
        private String foodName;
        private Long amount;
        // id 자동 생성
        // amount 기반 carbohydrate, protein, fat, kcal 계산 수행
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MealUpdateRequest {
        private String id;
        private Long userId;
        private Long amount;
        // id 기반 amount 수정만 지원
        // amount 기반 carbohydrate, protein, fat, kcal 재계산 수행
    }
}
