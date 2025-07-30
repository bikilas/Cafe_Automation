// src/main/java/com/kifiya/mapper/DrinkMapper.java
package com.kifiya.mapper;

import com.kifiya.dto.DrinkDto;
import com.kifiya.model.Drink;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", 
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DrinkMapper {
    DrinkMapper INSTANCE = Mappers.getMapper(DrinkMapper.class);
    
    DrinkDto toDto(Drink drink);
    Drink toEntity(DrinkDto drinkDto);
    
    void updateDrinkFromDto(DrinkDto dto, @MappingTarget Drink entity);
}