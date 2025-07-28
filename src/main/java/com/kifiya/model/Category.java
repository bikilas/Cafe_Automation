package com.kifiya.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String name;
    private String description;
@ManyToOne
@JoinColumn(name = "food_category_id")
private Category foodCategory;
   @ManyToOne
   @JsonIgnore
    private Cafe cafe;
}
