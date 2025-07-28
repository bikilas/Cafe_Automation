package com.kifiya.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class FoodResponse {
    private Long id;
    private String name;
    private String description;
    private String image;
    private double price;
    private boolean isAvailable;
    private boolean featured;
    private boolean isVegetarian;
    private boolean isSeasonal;
    private int popularity;
    private FoodCategoryDto foodCategory;
    private Long cafeId;
    private Date createdAt;
    private Date updatedAt;
    private List<String> images;
    private List<IngredientDto> ingredients;

    public static FoodResponse fromEntity(com.kifiya.model.Food food) {
        FoodResponse response = new FoodResponse();
        response.setId(food.getId());
        response.setName(food.getName());
        response.setDescription(food.getDescription());
        response.setImage(food.getImage());
        response.setPrice(food.getPrice());
        response.setAvailable(food.isAvailable());
        response.setFeatured(food.isFeatured());
        response.setVegetarian(food.isVegetarian());
        response.setSeasonal(food.isSeasonal());
        response.setPopularity(food.getPopularity());
        
        // Convert FoodCategory to FoodCategoryDto
        if (food.getFoodCategory() != null) {
            response.setFoodCategory(FoodCategoryDto.fromEntity(food.getFoodCategory()));
        }
        
        response.setCafeId(food.getCafe() != null ? food.getCafe().getId() : null);
        response.setCreatedAt(food.getCreatedAt());
        response.setUpdatedAt(food.getUpdatedAt());
        response.setImages(food.getImages());
        
        // Convert Ingredients to IngredientDto
        if (food.getIngredients() != null) {
            response.setIngredients(food.getIngredients().stream()
                .map(IngredientDto::fromEntity)
                .collect(Collectors.toList()));
        }
        
        return response;
    }
}
