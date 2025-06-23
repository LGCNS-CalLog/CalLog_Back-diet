package com.callog.callog_diet.domain.dto.meal;

import com.callog.callog_diet.domain.MealType;
import com.callog.callog_diet.domain.entity.Meal;
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
        private LocalDate date;
        private MealType mealType;
        private Long foodId;
        private String foodName;
        private Double amount;
        // id 자동 생성

        public Meal toEntity(Long userId) {
            Meal meal = new Meal();
            meal.setUserId(userId);
            meal.setDate(this.date);
            meal.setMealType(this.mealType);
            meal.setFoodId(this.foodId);
            meal.setFoodName(this.foodName);
            meal.setAmount(this.amount);

            return meal;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MealUpdateRequest {
        private Long id;
        private Double amount;
        // id 기반 amount 수정만 지원
        // amount 기반 carbohydrate, protein, fat, kcal 재계산 수행
    }
}
