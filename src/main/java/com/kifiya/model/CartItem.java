package com.kifiya.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

// import java.util.List;
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JsonIgnore
    private Cart cart;
//    @ManyToOne
//    private Food food;
    // private int quantity;
    // private List <String> ingredients;
    // private  long totalPrice;


}