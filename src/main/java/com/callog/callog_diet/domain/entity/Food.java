package com.callog.callog_diet.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Food {
    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double defaultAmount;

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
