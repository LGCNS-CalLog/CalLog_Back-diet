package com.callog.callog_diet.domain.repository;

import com.callog.callog_diet.domain.MealType;
import com.callog.callog_diet.domain.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findByUserIdAndDate(Long userId, LocalDate date);
    List<Meal> findByUserIdAndDateAndMealType(Long userId, LocalDate date, MealType mealType);
}
