// src/main/java/com/kifiya/model/Drink.java
package com.kifiya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "drinks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    private String description;
    
    @Column(nullable = false)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private DrinkCategory category;
    
    private boolean available = true;
    private String imageUrl;
    private int preparationTime; // in minutes
    private boolean customizable = false;
}