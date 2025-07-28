package com.kifiya.dto;

import com.kifiya.model.Ingredient;
import lombok.Data;

import java.util.Date;

@Data
public class IngredientDto {
    private Long id;
    private String name;
    private String description;
    private boolean isVegetarian;
    private boolean isVegan;
    private boolean isGlutenFree;
    private Date createdAt;
    private Date updatedAt;
    
    public static IngredientDto fromEntity(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        
        IngredientDto dto = new IngredientDto();
        dto.setId(ingredient.getId());
        dto.setName(ingredient.getName());
        dto.setDescription(ingredient.getDescription());
        dto.setVegetarian(ingredient.isVegetarian());
        dto.setVegan(ingredient.isVegan());
        dto.setGlutenFree(ingredient.isGlutenFree());
        dto.setCreatedAt(ingredient.getCreatedAt());
        dto.setUpdatedAt(ingredient.getUpdatedAt());
        return dto;
    }
}
