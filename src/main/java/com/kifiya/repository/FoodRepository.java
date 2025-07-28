package com.kifiya.repository;

import com.kifiya.model.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    
    // Find foods by name (case-insensitive)
    List<Food> findByNameContainingIgnoreCase(String name);
    
    // Find foods by category ID
    List<Food> findByFoodCategory_Id(Long categoryId);
    
    // Find foods by cafe ID
    List<Food> findByCafeId(Long cafeId);
    
    // Find foods by price range
    @Query("SELECT f FROM Food f WHERE f.price BETWEEN :minPrice AND :maxPrice")
    List<Food> findByPriceRange(
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
    
    // Find available foods
    List<Food> findByIsAvailableTrue();
    
    // Find vegetarian foods
    List<Food> findByIsVegetarianTrue();
    
    // Find seasonal foods
    List<Food> findByIsSeasonalTrue();
    
    // Find featured foods
    List<Food> findByFeaturedTrue();
    
    // Find foods by availability
    List<Food> findByIsAvailable(boolean isAvailable);

    // Find foods by name or category name containing the given search terms (case-insensitive)
    @Query("SELECT f FROM Food f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(f.foodCategory.name) LIKE LOWER(CONCAT('%', :category, '%'))")
    List<Food> findByNameContainingIgnoreCaseOrFoodCategory_NameContainingIgnoreCase(
            @Param("name") String name,
            @Param("category") String category
    );
    
    // Find most popular foods
    @Query("SELECT f FROM Food f ORDER BY f.popularity DESC")
    List<Food> findTopPopularFoods(Pageable pageable);
    
    // Find foods by category with pagination
    List<Food> findByFoodCategory_Id(Long categoryId, Pageable pageable);
    
    // Check if food exists by name and cafe
    boolean existsByNameAndCafeId(String name, Long cafeId);
    
    // Find by name and cafe ID
    Optional<Food> findByNameAndCafeId(String name, Long cafeId);
    
    // Find by ID and cafe ID
    Optional<Food> findByIdAndCafeId(Long id, Long cafeId);
}
