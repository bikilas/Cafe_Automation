package com.kifiya.repository;

import com.kifiya.model.Drink;
import com.kifiya.model.DrinkCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
 List<Drink> findByCategory(DrinkCategory category);
    List<Drink> findByAvailableTrue();
    List<Drink> findByNameContainingIgnoreCase(String name);
}