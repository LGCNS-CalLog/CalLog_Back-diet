package com.callog.callog_diet.domain.repository;

import com.callog.callog_diet.domain.Food;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findByNameContaining(String search);
}
