package com.callog.callog_diet.service;

import com.callog.callog_diet.common.exception.CustomException;
import com.callog.callog_diet.common.exception.DietErrorCode;
import com.callog.callog_diet.common.exception.FoodErrorCode;
import com.callog.callog_diet.domain.MealType;
import com.callog.callog_diet.domain.dto.meal.MealRequest;
import com.callog.callog_diet.domain.dto.meal.MealResponse;
import com.callog.callog_diet.domain.entity.Food;
import com.callog.callog_diet.domain.entity.Meal;
import com.callog.callog_diet.domain.repository.FoodRepository;
import com.callog.callog_diet.domain.repository.MealRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.callog.callog_diet.common.exception.DietErrorCode.DIET_FORBIDDEN;

@Service
@AllArgsConstructor
public class MealService {
    private final MealRepository mealRepository;
    private final FoodRepository foodRepository;

    public MealResponse.CreateUpdateMealResponse createMeal(Long userId, MealRequest.MealCreateRequest request) {
        // [1] 유효하지 않은 foodId일 경우, FOOD_NOT_FOUND
        Food food = foodRepository.findById(request.getFoodId())
                .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));
        // [2] foodId의 foodName과 요청의 foodName이 다를 경우, DIET_INVALID_INPUT
        if(!food.getName().equals(request.getFoodName())) {
            throw new CustomException(DietErrorCode.DIET_INVALID_INPUT);
        }
        Meal meal = request.toEntity(userId);

        // amount 기반 carbohydrate, protein, fat, kcal 계산 수행
        Double amount = meal.getAmount();
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

    public MealResponse.CreateUpdateMealResponse updateMeal(Long userId, MealRequest.MealUpdateRequest request) {
        // [1] 유효하지 않은 mealId일 경우, DIET_NOT_FOUND
        Meal meal = mealRepository.findById(request.getId())
                .orElseThrow(() -> new CustomException(DietErrorCode.DIET_NOT_FOUND));
        // [2] 유효하지 않은 foodId일 경우, FOOD_NOT_FOUND
        Food food = foodRepository.findById(meal.getFoodId())
                .orElseThrow(() -> new CustomException(FoodErrorCode.FOOD_NOT_FOUND));
        // [3] 로그인 사용자의 식단 기록이 아닌 경우, DIET_FORBIDDEN
        if (meal.getUserId() == null || !meal.getUserId().equals(userId)) {
            throw new CustomException(DietErrorCode.DIET_FORBIDDEN);
        }
        Double amount = request.getAmount();
        meal.setAmount(amount);
        meal.setCarbohydrate(food.getCarbohydrate() * amount);
        meal.setProtein(food.getProtein() * amount);
        meal.setFat(food.getFat() * amount);
        meal.setKcal(food.getKcal() * amount);
        mealRepository.save(meal);

        return MealResponse.CreateUpdateMealResponse.builder()
                .id(meal.getId())
                .date(meal.getDate())
                .mealType(meal.getMealType())
                .foodId(meal.getFoodId())
                .foodName(meal.getFoodName())
                .amount(meal.getAmount())
                .carbohydrate(meal.getCarbohydrate())
                .protein(meal.getProtein())
                .fat(meal.getFat())
                .kcal(meal.getKcal())
                .build();
    }

    public void deleteMeal(Long userId, Long mealId) {
        // [1] 유효하지 않은 mealId일 경우, DIET_NOT_FOUND
        Meal meal = mealRepository.findById(mealId)
                .orElseThrow(() -> new CustomException(DietErrorCode.DIET_NOT_FOUND));
        // [2] 로그인 사용자의 식단 기록이 아닌 경우, DIET_FORBIDDEN
        if(meal.getUserId() == null || !meal.getUserId().equals(userId)) {
            throw new CustomException(DietErrorCode.DIET_FORBIDDEN);
        }
        mealRepository.deleteById(mealId);
    }

}
