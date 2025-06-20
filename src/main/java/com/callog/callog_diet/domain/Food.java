package com.callog.callog_diet.domain;

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
    private double defaultAmount;

    @Column
    private double carbohydrate;

    @Column
    private double protein;

    @Column
    private double fat;

    @Column
    private double kcal;

}
