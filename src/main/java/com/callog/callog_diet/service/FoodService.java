package com.callog.callog_diet.service;

import com.callog.callog_diet.domain.entity.Food;
import com.callog.callog_diet.domain.dto.food.FoodResponse;
import com.callog.callog_diet.domain.repository.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodService {
    @Autowired
    FoodRepository foodRepository;

    public Page<FoodResponse.FoodListResponse> getFoodListWithCount(String search, Pageable pageable) {
        log.info("foodService getFoodList 호출 {}", search);
        Page<Food> foodList;


        foodList = foodRepository.findByNameContaining(search,pageable);
       return convertFoodPageToFoodListResponsePage(foodList,pageable);

    }
    public Page<FoodResponse.FoodListResponse> convertFoodPageToFoodListResponsePage(Page<Food> foodPage, Pageable pageable) {
        List<FoodResponse.FoodListResponse> foodListResponses = foodPage.getContent().stream()
                .map(food -> FoodResponse.FoodListResponse.builder()
                        .id(food.getId())
                        .name(food.getName())
                        .defaultAmount(food.getDefaultAmount())
                        .carbohydrate(food.getCarbohydrate())
                        .protein(food.getProtein())
                        .fat(food.getFat())
                        .kcal(food.getKcal())
                        .sugar(food.getSugar())
                        .fiber(food.getFiber())
                        .build())
                .collect(Collectors.toList());

        return new PageImpl<>(foodListResponses, pageable, foodPage.getTotalElements());
    }
}
