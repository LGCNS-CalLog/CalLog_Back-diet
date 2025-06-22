package com.callog.callog_diet.domain.entity;

import com.callog.callog_diet.domain.MealType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Meal {
    @Id
    private String id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private MealType mealType;

    @Column(nullable = false)
    private Long foodId;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private Long amount;

    @Column
    private double carbohydrate;

    @Column
    private double protein;

    @Column
    private double fat;

    @Column
    private double kcal;
}
