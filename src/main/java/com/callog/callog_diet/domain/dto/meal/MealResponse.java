package com.callog.callog_diet.domain.dto.meal;

import com.callog.callog_diet.domain.MealType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

public class MealResponse {

    @Data
    @Builder
    public static class DateMealListResponse {
        private Long id;
        private MealType mealType;
        private Long foodId;
        private String foodName;
        private Double amount;
        private Double carbohydrate;
        private Double protein;
        private Double fat;
        private Double kcal;
        private Double sugar;
        private Double fiber;
    }

    @Data
    @Builder
    public static class DateMealTypeListResponse {
        private Long id;
        private Long foodId;
        private String foodName;
        private Double amount;
        private Double carbohydrate;
        private Double protein;
        private Double fat;
        private Double kcal;
        private Double sugar;
        private Double fiber;
    }

    @Data
    @Builder
    public static class CreateUpdateMealResponse {
        private Long id;
        private LocalDate date;
        private MealType mealType;
        private Long foodId;
        private String foodName;
        private Double amount;
        private Double carbohydrate;
        private Double protein;
        private Double fat;
        private Double kcal;
        private Double sugar;
        private Double fiber;
    }
}
