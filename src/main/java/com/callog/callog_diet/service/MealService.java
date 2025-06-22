package com.callog.callog_diet.service;

import com.callog.callog_diet.common.exception.CustomException;
import com.callog.callog_diet.common.exception.ErrorCode;
import com.callog.callog_diet.common.exception.FoodErrorCode;
import com.callog.callog_diet.domain.MealType;
import com.callog.callog_diet.domain.dto.meal.MealRequest;
import com.callog.callog_diet.domain.dto.meal.MealResponse;
import com.callog.callog_diet.domain.entity.Food;
import com.callog.callog_diet.domain.entity.Meal;
import com.callog.callog_diet.domain.repository.FoodRepository;
import com.callog.callog_diet.domain.repository.MealRepository;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final FoodRepository foodRepository;

    public MealResponse.CreateUpdateMealResponse createMeal(Long userId, MealRequest.MealCreateRequest request) {
        Food food = foodRepository.findById(request.getFoodId())
                .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));
        // TODO: foodId와 foodName이 매칭되는지 확인 필요 -> 일치하지 않을 경우 오류 발생
        Meal meal = request.toEntity(userId);

        // amount 기반 carbohydrate, protein, fat, kcal 계산 수행
        Long amount = meal.getAmount();
        meal.setCarbohydrate(amount * food.getCarbohydrate());
        meal.setProtein(amount * food.getProtein());
        meal.setFat(amount * food.getFat());
        meal.setKcal(amount * food.getKcal());

        Meal m = mealRepository.save(meal);
        return MealResponse.CreateUpdateMealResponse.builder()
                .id(m.getId())
                .date(m.getDate())
                .mealType(m.getMealType())
                .foodId(m.getFoodId())
                .foodName(m.getFoodName())
                .amount(m.getAmount())
                .carbohydrate(m.getCarbohydrate())
                .protein(m.getProtein())
                .fat(m.getFat())
                .kcal(m.getKcal())
                .build();
    }

    public List<MealResponse.DateMealListResponse> readDateMealList(Long userId, LocalDate date) {
        List<Meal> dateMealList = mealRepository.findByUserIdAndDate(userId, date);

        return dateMealList.stream()
                .map(m -> MealResponse.DateMealListResponse.builder()
                        .id(m.getId())
                        .mealType(m.getMealType())
                        .foodId(m.getFoodId())
                        .foodName(m.getFoodName())
                        .amount(m.getAmount())
                        .carbohydrate(m.getCarbohydrate())
                        .protein(m.getProtein())
                        .fat(m.getFat())
                        .kcal(m.getKcal())
                        .build()
                ).toList();
    }

    public List<MealResponse.DateMealTypeListResponse> readDateMealTypeList(Long userId, LocalDate date, MealType mealType) {
        List<Meal> dateMealTypeList = mealRepository.findByUserIdAndDateAndMealType(userId, date, mealType);

        return dateMealTypeList.stream()
                .map(m -> MealResponse.DateMealTypeListResponse.builder()
                        .id(m.getId())
                        .foodId(m.getFoodId())
                        .foodName(m.getFoodName())
                        .amount(m.getAmount())
                        .carbohydrate(m.getCarbohydrate())
                        .protein(m.getProtein())
                        .fat(m.getFat())
                        .kcal(m.getKcal())
                        .build()
                ).toList();
    }

//    public MealResponse.CreateUpdateMealResponse updateMeal(Long userId, MealRequest.MealUpdateRequest) {
//
//    }
//    deleteMeal

}
