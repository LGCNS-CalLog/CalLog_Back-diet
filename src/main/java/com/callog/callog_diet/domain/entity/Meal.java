package com.callog.callog_diet.domain.entity;

import com.callog.callog_diet.domain.MealType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @Column(nullable = false)
    private Long foodId;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private Double amount;

    @Column
    private Double carbohydrate;

    @Column
    private Double protein;

    @Column
    private Double fat;

    @Column
    private Double kcal;

    @Column
    private Double sugar;

    @Column
    private Double fiber;
}
