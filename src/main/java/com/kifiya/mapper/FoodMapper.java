package com.kifiya.mapper;

import com.kifiya.dto.FoodRequest;
import com.kifiya.dto.FoodResponse;
import com.kifiya.model.Cafe;
import com.kifiya.model.Food;
import com.kifiya.model.FoodCategory;
import com.kifiya.model.Ingredient;
import com.kifiya.dto.IngredientDto;
import com.kifiya.service.CafeService;
import com.kifiya.service.FoodCategoryService;
import com.kifiya.service.IngredientService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class FoodMapper {

    @Autowired
    protected FoodCategoryService foodCategoryService;
    
    @Autowired
    protected CafeService cafeService;
    
    @Autowired
    protected IngredientService ingredientService;

    @Mapping(target = "foodCategory", source = "foodCategoryId", qualifiedByName = "mapFoodCategory")
    @Mapping(target = "cafe", source = "cafeId", qualifiedByName = "mapCafe")
    @Mapping(target = "ingredients", source = "ingredientIds", qualifiedByName = "mapIngredientIds")
    public abstract Food toEntity(FoodRequest foodRequest);

    @Mapping(target = "foodCategory", source = "foodCategory")
    @Mapping(target = "cafeId", source = "cafe.id")
    @Mapping(target = "ingredients", source = "ingredients")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "available", source = "available")
    @Mapping(target = "featured", source = "featured")
    @Mapping(target = "vegetarian", source = "vegetarian")
    @Mapping(target = "seasonal", source = "seasonal")
    @Mapping(target = "popularity", source = "popularity")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "images", source = "images")
    public abstract FoodResponse toDto(Food food);

    @Named("mapFoodCategory")
    protected FoodCategory mapFoodCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return foodCategoryService.getFoodCategoryById(categoryId);
    }

    @Named("mapCafe")
    protected com.kifiya.model.Cafe mapCafe(Long cafeId) {
        if (cafeId == null) {
            return null;
        }
        return (Cafe) cafeService.getCafeById(cafeId);
    }

    @Named("mapIngredientIds")
    protected List<Ingredient> mapIngredientIds(List<Long> ingredientIds) {
        if (ingredientIds == null) {
            return null;
        }
        return ingredientService.findAllByIds(ingredientIds);
    }

    // This method is no longer needed as we're mapping ingredients directly
    @Named("mapIngredients")
    protected List<IngredientDto> mapIngredients(List<Ingredient> ingredients) {
        if (ingredients == null) {
            return null;
        }
        return ingredients.stream()
                .map(ingredient -> {
                    IngredientDto dto = new IngredientDto();
                    dto.setId(ingredient.getId());
                    dto.setName(ingredient.getName());
                    // Add other necessary fields from Ingredient to IngredientDto
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
