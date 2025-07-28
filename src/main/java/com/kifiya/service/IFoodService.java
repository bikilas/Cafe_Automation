package com.kifiya.service;

import com.kifiya.dto.FoodRequest;
import com.kifiya.dto.FoodResponse;

import java.util.List;

public interface IFoodService {
    List<FoodResponse> getAllFoods();
    FoodResponse getFoodById(Long id);
    FoodResponse createFood(FoodRequest foodRequest);
    FoodResponse updateFood(Long id, FoodRequest foodRequest);
    void deleteFood(Long id);
    List<FoodResponse> searchFoods(String name, String category);
    List<FoodResponse> getFoodsByCategory(Long categoryId);
    List<FoodResponse> getFoodsByCafe(Long cafeId);
    List<FoodResponse> getFeaturedFoods();
    List<FoodResponse> getVegetarianFoods();
    List<FoodResponse> getSeasonalFoods();
}
