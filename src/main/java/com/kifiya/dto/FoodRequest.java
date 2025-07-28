package com.kifiya.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class FoodRequest {
    @NotBlank(message = "Food name is required")
    private String name;
    
    private String description;
    private String image;
    
    @Positive(message = "Price must be a positive number")
    private double price;
    
    private boolean isAvailable = true;
    private boolean featured = false;
    private boolean isVegetarian = false;
    private boolean isSeasonal = false;
    
    @NotNull(message = "Food category ID is required")
    private Long foodCategoryId;
    
    @NotNull(message = "Cafe ID is required")
    private Long cafeId;
    
    private List<String> images;
    private List<Long> ingredientIds;
}
