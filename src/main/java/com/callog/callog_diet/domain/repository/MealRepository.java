package com.callog.callog_diet.domain.repository;

import com.callog.callog_diet.domain.MealType;
import com.callog.callog_diet.domain.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserIdAndDate(Long userId, LocalDate date);
    List<Meal> findByUserIdAndDateAndMealType(Long userId, LocalDate date, MealType mealType);
    List<Meal> findAllByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    Optional<Meal> findByUserIdAndDateAndMealTypeAndFoodId(Long userId, LocalDate date, MealType mealType, Long foodId);
    List<Meal> findByDateAndMealType(LocalDate now, MealType mealType);
}
