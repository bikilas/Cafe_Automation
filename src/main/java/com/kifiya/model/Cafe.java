package com.kifiya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
// @NoArgsConstructor
@Table(name = "cafes")
public class Cafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    private String description;
    private String type;
    private String status = "ACTIVE";
    private Boolean isOperational = true;
    private Integer capacity;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private Double averageRating;
    private Integer reviewCount;

    // This field stores the available coffee beans in kilograms
    @Column(nullable = false, columnDefinition = "integer default 0")
    private int coffeeBeansKg = 0;

    // One-to-Many relationship with Food
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Food> foods = new ArrayList<>();

    // One-to-Many relationship with Category (assuming Category belongs to a Cafe)
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Category> drinks = new ArrayList<>();

    // One-to-Many relationship with Order
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    // Many-to-Many relationship with your new Feature entity
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "cafe_features",
            joinColumns = @JoinColumn(name = "cafe_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Feature> features = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;


    // You can add methods to easily manage the bidirectional relationship
    public void addFeature(Feature feature) {
        this.features.add(feature);
        feature.getCafes().add(this);
    }

    public void removeFeature(Feature feature) {
        this.features.remove(feature);
        feature.getCafes().remove(this);
    }
}