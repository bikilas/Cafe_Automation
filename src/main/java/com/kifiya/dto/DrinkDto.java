package com.kifiya.dto;

import lombok.Data;
import java.math.BigDecimal;

import com.kifiya.model.DrinkCategory;

@Data
public class DrinkDto {
    private Long id;
    private String name;

    private String description;
    private BigDecimal price;

    private DrinkCategory category;
    private boolean available;
    private String imageUrl;
    private int preparationTime;
    private boolean customizable;
}