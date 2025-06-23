package com.callog.callog_diet.service;

import com.callog.callog_diet.domain.entity.Food;
import com.callog.callog_diet.domain.dto.food.FoodResponse;
import com.callog.callog_diet.domain.repository.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodService {
    @Autowired
    FoodRepository foodRepository;

    public List<FoodResponse.FoodListResponse> getFoodList(String search) {
        log.info("foodService getFoodList 호출 {}", search);
        List<Food> foodList;

        if (search == null || search.isBlank()) {
            foodList = new ArrayList<>();
            log.info("search 공백");
;        } else {
            foodList = foodRepository.findByNameContaining(search);
        }

        return foodList.stream()
                .map(food -> FoodResponse.FoodListResponse.builder()
                        .id(food.getId())
                        .name(food.getName())
                        .defaultAmount(food.getDefaultAmount())
                        .carbohydrate(food.getCarbohydrate())
                        .protein(food.getProtein())
                        .fat(food.getFat())
                        .kcal(food.getKcal())
                        .build())
                .collect(Collectors.toList());
    }

    public FoodResponse.FoodListWithCountResponse getFoodListWithCount(String search) {
        log.info("foodService getFoodList 호출 {}", search);
        List<Food> foodList;

        if (search == null || search.isBlank()) {
            foodList = new ArrayList<>();
            log.info("search 공백");
        } else {
            foodList = foodRepository.findByNameContaining(search);
        }

        List<FoodResponse.FoodListResponse> responseList = foodList.stream()
                .map(food -> FoodResponse.FoodListResponse.builder()
                        .id(food.getId())
                        .name(food.getName())
                        .defaultAmount(food.getDefaultAmount())
                        .carbohydrate(food.getCarbohydrate())
                        .protein(food.getProtein())
                        .fat(food.getFat())
                        .kcal(food.getKcal())
                        .build())
                .collect(Collectors.toList());

        return FoodResponse.FoodListWithCountResponse.builder()
                .totalCount(responseList.size())
                .foodList(responseList)
                .build();
    }

}
