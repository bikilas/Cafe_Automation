package com.kifiya.service;

import com.kifiya.dto.DrinkDto;
import com.kifiya.exception.ResourceNotFoundException;
import com.kifiya.mapper.DrinkMapper;
import com.kifiya.model.Drink;
import com.kifiya.repository.DrinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DrinkService {
    private final DrinkRepository drinkRepository;
    private final DrinkMapper drinkMapper;

    @Transactional(readOnly = true)
    public List<DrinkDto> getAllDrinks() {
        return drinkRepository.findAll().stream()
                .map(drinkMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DrinkDto getDrinkById(Long id) {
        return drinkRepository.findById(id)
                .map(drinkMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Drink not found with id: " + id));
    }

    @Transactional
    public DrinkDto createDrink(DrinkDto drinkDto) {
        Drink drink = drinkMapper.toEntity(drinkDto);
        Drink savedDrink = drinkRepository.save(drink);
        return drinkMapper.toDto(savedDrink);
    }

    @Transactional
    public DrinkDto updateDrink(Long id, DrinkDto drinkDto) {
        Drink existingDrink = drinkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drink not found with id: " + id));
        
        drinkMapper.updateDrinkFromDto(drinkDto, existingDrink);
        Drink updatedDrink = drinkRepository.save(existingDrink);
        return drinkMapper.toDto(updatedDrink);
    }

    @Transactional
    public void deleteDrink(Long id) {
        if (!drinkRepository.existsById(id)) {
            throw new ResourceNotFoundException("Drink not found with id: " + id);
        }
        drinkRepository.deleteById(id);
    }
}