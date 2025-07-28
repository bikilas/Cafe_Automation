package com.kifiya.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "foods")
@Data
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    private String description;
    private String image;
    private double price;
    private boolean isAvailable = true;
    private boolean featured = false;
    private boolean isVegetarian = false;
    private boolean isSeasonal = false;
    private int popularity = 0;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "food_category_id")
    private FoodCategory foodCategory;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;
    
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    @ElementCollection
    @CollectionTable(name = "food_images", joinColumns = @JoinColumn(name = "food_id"))
    @Column(name = "image_url")
    private List<String> images;

    @ManyToMany
    @JoinTable(
        name = "food_ingredients",
        joinColumns = @JoinColumn(name = "food_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;
}
