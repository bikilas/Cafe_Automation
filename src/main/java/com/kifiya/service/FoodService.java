package com.kifiya.service;

import com.kifiya.dto.FoodRequest;
import com.kifiya.dto.FoodResponse;
import com.kifiya.exception.ResourceNotFoundException;
import com.kifiya.model.Cafe;
import com.kifiya.model.Food;
import com.kifiya.model.FoodCategory;
import com.kifiya.model.Ingredient;
import com.kifiya.repository.CafeRepository;
import com.kifiya.repository.FoodCategoryRepository;
import com.kifiya.repository.FoodRepository;
import com.kifiya.repository.IngredientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodService implements IFoodService {
    
    @Autowired
    private FoodRepository foodRepository;
    
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;
    
    @Autowired
    private CafeRepository cafeRepository;
    
    @Autowired
    private IngredientRepository ingredientRepository;

    public List<FoodResponse> getAllFoods() {
        return foodRepository.findAll().stream()
            .map(FoodResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public FoodResponse getFoodById(Long id) {
        return foodRepository.findById(id)
            .map(FoodResponse::fromEntity)
            .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));
    }

    @Transactional
    public FoodResponse createFood(FoodRequest foodRequest) {
        // Find and validate food category
        FoodCategory foodCategory = foodCategoryRepository.findById(foodRequest.getFoodCategoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Food category not found with id: " + foodRequest.getFoodCategoryId()));
        
        // Find and validate cafe
        Cafe cafe = cafeRepository.findById(foodRequest.getCafeId())
            .orElseThrow(() -> new ResourceNotFoundException("Cafe not found with id: " + foodRequest.getCafeId()));
        
        // Create new food
        Food food = new Food();
        food.setName(foodRequest.getName());
        food.setDescription(foodRequest.getDescription());
        food.setImage(foodRequest.getImage());
        food.setPrice(foodRequest.getPrice());
        food.setAvailable(foodRequest.isAvailable());
        food.setFeatured(foodRequest.isFeatured());
        food.setVegetarian(foodRequest.isVegetarian());
        food.setSeasonal(foodRequest.isSeasonal());
        food.setFoodCategory(foodCategory);
        food.setCafe(cafe);
        
        // Set images if provided
        if (foodRequest.getImages() != null) {
            food.setImages(foodRequest.getImages());
        }
        
        // Set ingredients if provided
        if (foodRequest.getIngredientIds() != null && !foodRequest.getIngredientIds().isEmpty()) {
            List<Ingredient> ingredients = ingredientRepository.findAllById(foodRequest.getIngredientIds());
            food.setIngredients(ingredients);
        }
        
        Food savedFood = foodRepository.save(food);
        return FoodResponse.fromEntity(savedFood);
    }

    @Transactional
    public FoodResponse updateFood(Long id, FoodRequest foodRequest) {
        Food existingFood = foodRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Food not found with id: " + id));
        
        // Update basic fields
        existingFood.setName(foodRequest.getName());
        existingFood.setDescription(foodRequest.getDescription());
        existingFood.setImage(foodRequest.getImage());
        existingFood.setPrice(foodRequest.getPrice());
        existingFood.setAvailable(foodRequest.isAvailable());
        existingFood.setFeatured(foodRequest.isFeatured());
        existingFood.setVegetarian(foodRequest.isVegetarian());
        existingFood.setSeasonal(foodRequest.isSeasonal());
        
        // Update food category if changed
        if (!existingFood.getFoodCategory().getId().equals(foodRequest.getFoodCategoryId())) {
            FoodCategory foodCategory = foodCategoryRepository.findById(foodRequest.getFoodCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Food category not found with id: " + foodRequest.getFoodCategoryId()));
            existingFood.setFoodCategory(foodCategory);
        }
        
        // Update cafe if changed
        if (!existingFood.getCafe().getId().equals(foodRequest.getCafeId())) {
            Cafe cafe = cafeRepository.findById(foodRequest.getCafeId())
                .orElseThrow(() -> new ResourceNotFoundException("Cafe not found with id: " + foodRequest.getCafeId()));
            existingFood.setCafe(cafe);
        }
        
        // Update images if provided
        if (foodRequest.getImages() != null) {
            existingFood.setImages(foodRequest.getImages());
        }
        
        // Update ingredients if provided
        if (foodRequest.getIngredientIds() != null) {
            List<Ingredient> ingredients = ingredientRepository.findAllById(foodRequest.getIngredientIds());
            existingFood.setIngredients(ingredients);
        }
        
        Food updatedFood = foodRepository.save(existingFood);
        return FoodResponse.fromEntity(updatedFood);
    }

    @Transactional
    public void deleteFood(Long id) {
        if (!foodRepository.existsById(id)) {
            throw new ResourceNotFoundException("Food not found with id: " + id);
        }
        foodRepository.deleteById(id);
    }

    public List<FoodResponse> searchFoods(String name, String category) {
        return foodRepository.findByNameContainingIgnoreCaseOrFoodCategory_NameContainingIgnoreCase(name, category)
            .stream()
            .map(FoodResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<FoodResponse> getFoodsByCategory(Long categoryId) {
        return foodRepository.findByFoodCategory_Id(categoryId)
            .stream()
            .map(FoodResponse::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public List<FoodResponse> getVegetarianFoods() {
        return foodRepository.findByIsVegetarianTrue().stream()
            .map(FoodResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<FoodResponse> getFeaturedFoods() {
        return foodRepository.findByFeaturedTrue().stream()
            .map(FoodResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<FoodResponse> getSeasonalFoods() {
        return foodRepository.findByIsSeasonalTrue().stream()
            .map(FoodResponse::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    public List<FoodResponse> getFoodsByCafe(Long cafeId) {
        return foodRepository.findByCafeId(cafeId).stream()
            .map(FoodResponse::fromEntity)
            .collect(Collectors.toList());
    }
}
