package com.kifiya.service;

import com.kifiya.model.Ingredient;

import java.util.List;

public interface IngredientService {
    List<Ingredient> findAllByIds(List<Long> ids);
    // Add other ingredient-related methods as needed
}
