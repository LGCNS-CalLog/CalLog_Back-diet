package com.callog.callog_diet.domain.dto.food;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class FoodResponse {

    @Data
    @Builder
    public static class FoodListResponse {
        private Long id;
        private String name;
        private Double defaultAmount;
        private Double carbohydrate;
        private Double protein;
        private Double fat;
        private Double kcal;
    }

    @Data
    @Builder
    public static class FoodListWithCountResponse {
        private int totalCount;
        private List<FoodListResponse> foodList;
    }
}
