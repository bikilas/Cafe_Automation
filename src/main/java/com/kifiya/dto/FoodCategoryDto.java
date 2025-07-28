package com.kifiya.dto;

import com.kifiya.model.FoodCategory;
import lombok.Data;

import java.util.Date;

@Data
public class FoodCategoryDto {
    private Long id;
    private String name;
    private String description;
    private Date createdAt;
    private Date updatedAt;
    
    public static FoodCategoryDto fromEntity(FoodCategory category) {
        if (category == null) {
            return null;
        }
        
        FoodCategoryDto dto = new FoodCategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }
}
