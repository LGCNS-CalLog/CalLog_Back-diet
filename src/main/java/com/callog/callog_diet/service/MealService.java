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
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (!food.getName().equals(request.getFoodName())) {
            throw new CustomException(DietErrorCode.DIET_INVALID_INPUT);
        }
        // [3] 이미 존재하는 식단인지 확인 (date, mealType, foodId)
        Optional<Meal> optionalMeal = mealRepository.findByUserIdAndDateAndMealTypeAndFoodId(
                userId, request.getDate(), request.getMealType(), request.getFoodId());

        Meal meal;
        Double amount;
        // [3-1] date, mealType, foodId ▶ 이미 존재할 경우, 양 추가
        if (optionalMeal.isPresent()) {
            meal = optionalMeal.get();
            amount = meal.getAmount() + request.getAmount();
        } else {  // [3-2] 없을 경우, 새 식단 추가
            meal = request.toEntity(userId);
            amount = request.getAmount();
        }

        applyNutrition(meal, food, amount);

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

    public List<LocalDate> readMonthMealList(Long userId, YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1); // 해당 월의 첫날
        LocalDate endDate = yearMonth.atEndOfMonth(); // 해당 월의 마지막 날

        List<Meal> meals = mealRepository.findAllByUserIdAndDateBetween(userId, startDate, endDate);

        return meals.stream()
                .map(Meal::getDate)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
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
        applyNutrition(meal, food, amount);
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

    public void deleteMeal(Long userId, MealRequest.MealDeleteRequest request) {
        // [1] 유효하지 않은 mealId일 경우, DIET_NOT_FOUND
        Meal meal = mealRepository.findById(request.getMealId())
                .orElseThrow(() -> new CustomException(DietErrorCode.DIET_NOT_FOUND));
        // [2] 로그인 사용자의 식단 기록이 아닌 경우, DIET_FORBIDDEN
        if(meal.getUserId() == null || !meal.getUserId().equals(userId)) {
            throw new CustomException(DietErrorCode.DIET_FORBIDDEN);
        }
        mealRepository.deleteById(request.getMealId());
    }

    private void applyNutrition(Meal meal, Food food, double amount) {
        meal.setAmount(amount);
        meal.setCarbohydrate(food.getCarbohydrate() * amount);
        meal.setProtein(food.getProtein() * amount);
        meal.setFat(food.getFat() * amount);
        meal.setKcal(food.getKcal() * amount);
    }

}
