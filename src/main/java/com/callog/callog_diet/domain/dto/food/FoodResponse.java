package com.callog.callog_diet.domain.dto.food;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

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
        private Double sugar;
        private Double fiber;
    }
}
