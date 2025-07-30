// src/main/java/com/kifiya/model/CartItem.java
package com.kifiya.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drink_id", nullable = false)
    private Drink drink;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity = 1;
    
    // For customizations
    private String specialInstructions;
    private String milkType; // e.g., WHOLE, ALMOND, SOY
    private String sweetnessLevel; // e.g., LESS_SUGAR, NORMAL, EXTRA_SUGAR
    private boolean iced = false;
    
    // Calculated fields
    // public BigDecimal getSubtotal() {
    //     return drink.getPrice().multiply(BigDecimal.valueOf(quantity));
    // }
}