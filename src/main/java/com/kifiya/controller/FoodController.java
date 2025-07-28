
package com.kifiya.controller;

import com.kifiya.dto.FoodRequest;
import com.kifiya.dto.FoodResponse;
import com.kifiya.service.IFoodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
@RequiredArgsConstructor
public class FoodController {
    
    private final IFoodService foodService;

    @GetMapping
    public ResponseEntity<List<FoodResponse>> getAllFoods() {
        List<FoodResponse> foods = foodService.getAllFoods();
        return ResponseEntity.ok(foods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodResponse> getFoodById(@PathVariable Long id) {
        return ResponseEntity.ok(foodService.getFoodById(id));
    }

    @PostMapping
    public ResponseEntity<FoodResponse> createFood(@Valid @RequestBody FoodRequest foodRequest) {
        FoodResponse createdFood = foodService.createFood(foodRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFood);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodResponse> updateFood(
            @PathVariable Long id, 
            @Valid @RequestBody FoodRequest foodRequest) {
        return ResponseEntity.ok(foodService.updateFood(id, foodRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodResponse>> searchFoods(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category) {
        return ResponseEntity.ok(foodService.searchFoods(name, category));
    }

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<FoodResponse>> getFoodsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(foodService.getFoodsByCategory(categoryId));
    }
    
    @GetMapping("/by-cafe/{cafeId}")
    public ResponseEntity<FoodResponse> getFoodsByCafe(@PathVariable Long cafeId) {
        return ResponseEntity.ok(foodService.getFoodById(cafeId));
    }
    
    @GetMapping("/featured")
    public ResponseEntity<List<FoodResponse>> getFeaturedFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }
    
    @GetMapping("/vegetarian")
    public ResponseEntity<Object> getVegetarianFoods() {
        return ResponseEntity.ok(foodService.getVegetarianFoods());
    }
    
    @GetMapping("/seasonal")
    public ResponseEntity<List<FoodResponse>> getSeasonalFoods() {
        return ResponseEntity.ok(foodService.getAllFoods());
    }
}