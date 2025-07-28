package com.kifiya.service.impl;

import com.kifiya.exception.ResourceNotFoundException;
import com.kifiya.model.FoodCategory;
import com.kifiya.repository.FoodCategoryRepository;
import com.kifiya.service.FoodCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FoodCategoryServiceImpl implements FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;

    @Autowired
    public FoodCategoryServiceImpl(FoodCategoryRepository foodCategoryRepository) {
        this.foodCategoryRepository = foodCategoryRepository;
    }

    @Override
    public FoodCategory getFoodCategoryById(Long id) {
        return foodCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food category not found with id: " + id));
    }
    
    // Add other food category-related service methods as needed
}
