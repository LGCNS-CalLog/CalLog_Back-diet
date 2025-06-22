package com.callog.callog_diet.domain.repository;

import com.callog.callog_diet.domain.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
