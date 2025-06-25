package com.callog.callog_diet.domain.repository;

import com.callog.callog_diet.domain.entity.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Page<Food> findByNameContaining(String search, Pageable pageable);
}
