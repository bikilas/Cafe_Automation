package com.kifiya.mapper;

import com.kifiya.dto.FoodRequest;
import com.kifiya.dto.FoodResponse;
import com.kifiya.model.Cafe;
import com.kifiya.model.Food;
import com.kifiya.model.FoodCategory;
import com.kifiya.model.Ingredient;
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
    @Mapping(target = "ingredients", source = "ingredientIds", qualifiedByName = "mapIngredients")
    public abstract Food toEntity(FoodRequest foodRequest);

    @Mapping(target = "foodCategoryId", source = "foodCategory.id")
    @Mapping(target = "cafeId", source = "cafe.id")
    @Mapping(target = "ingredientIds", source = "ingredients", qualifiedByName = "mapIngredientIds")
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

    @Named("mapIngredients")
    protected List<Ingredient> mapIngredients(List<Long> ingredientIds) {
        if (ingredientIds == null) {
            return null;
        }
        return ingredientService.findAllByIds(ingredientIds);
    }

    @Named("mapIngredientIds")
    protected List<Long> mapIngredientIds(List<Ingredient> ingredients) {
        if (ingredients == null) {
            return null;
        }
        return ingredients.stream()
                .map(Ingredient::getId)
                .collect(Collectors.toList());
    }
}
