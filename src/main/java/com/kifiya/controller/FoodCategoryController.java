package com.kifiya.controller;

import com.kifiya.dto.FoodCategoryRequest;
import com.kifiya.model.FoodCategory;
import com.kifiya.repository.FoodCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food-categories")
public class FoodCategoryController {
    
    private final FoodCategoryRepository foodCategoryRepository;

    public FoodCategoryController(FoodCategoryRepository foodCategoryRepository) {
        this.foodCategoryRepository = foodCategoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<FoodCategory>> getAllFoodCategories() {
        return ResponseEntity.ok(foodCategoryRepository.findAll());
    }

   @PostMapping
   public ResponseEntity<FoodCategory> createFoodCategory(@RequestBody FoodCategoryRequest request) {
       FoodCategory category = new FoodCategory();
       category.setName(request.getName());
       category.setDescription(request.getDescription());
       return ResponseEntity.ok(foodCategoryRepository.save(category));

   }
   

}
