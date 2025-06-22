package com.callog.callog_diet.domain.dto.meal;

import com.callog.callog_diet.domain.MealType;
import lombok.Builder;
import lombok.Data;

public class MealResponse {

    @Data
    @Builder
    public static class DateMealListResponse {
        private String id;
        private MealType mealType;
        private Long foodId;
        private String foodName;
        private Long amount;
        private double carbohydrate;
        private double protein;
        private double fat;
        private double kcal;
    }

    @Data
    @Builder
    public static class DateMealTypeListResponse {
        private String id;
        private Long foodId;
        private String foodName;
        private Long amount;
        private double carbohydrate;
        private double protein;
        private double fat;
        private double kcal;
    }


}
