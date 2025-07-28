package com.kifiya.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "features") // This will create a table named 'features' in your database
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // This field will store the actual name of the feature (e.g., "WiFi", "Pet-friendly")
    private String name;

    // Optional: A brief description of the feature
    private String description;

    // Bidirectional Many-to-Many relationship with Cafe.
    // 'mappedBy' indicates that the 'features' field in the Cafe entity is the owner of this relationship.
    @ManyToMany(mappedBy = "features")
    // Use Lombok's exclude to prevent infinite loops in toString() and equals/hashCode()
    // for bidirectional relationships.
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Cafe> cafes = new HashSet<>(); // Initialize to prevent NullPointerException

    // Default constructor for JPA. Required by Hibernate.
    public Feature() {}

    // Constructor to easily create a Feature with just a name.
    public Feature(String name) {
        this.name = name;
    }

    // You can add more constructors, getters, and setters if Lombok's @Data
    // doesn't generate exactly what you need, but @Data should cover most cases.
}