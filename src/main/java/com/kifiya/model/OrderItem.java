package com.kifiya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long foodId;
    private String foodName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subtotal;
    private String notes;
}

// // Assuming you have other fields for your OrderItem like quantity, price, etc.

// @Entity
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Table(name = "order_items") // It's good practice to explicitly name your tables, and use plural.
// // Based on your 'show tables;', you have 'order_item' and 'order_items'.
// // Choose one, or ensure your entity maps to the correct one.
// // Let's assume 'order_items' for consistency with the 'items' list name.
// public class OrderItem {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     // --- CRUCIAL CHANGE: Add the ManyToOne relationship to Order ---
//     @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch is usually good for performance
//     @JoinColumn(name = "order_id", nullable = false) // This will create the foreign key column 'order_id'
//     private Order order; // This name "order" must match the 'mappedBy' value in the Order entity

//     // Other fields for OrderItem (e.g., quantity, price, reference to a menu item/product)
//     private int quantity;
//     private double itemPrice; // Price of this specific item in the order

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "cart_item_id") // Assuming an OrderItem refers to a MenuItem
//     private CartItem cartItem; // You'll need a MenuItem entity as well

//     public void setDescription(Object description) {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'setDescription'");
//     }

//     public void setItemPrice(BigDecimal price) {
//         // TODO Auto-generated method stub
//         throw new UnsupportedOperationException("Unimplemented method 'setItemPrice'");
//     }

//     // You might also link to Food orr Product directly depending on your design
//     // @ManyToOne(fetch = FetchType.LAZY)
//     // @JoinColumn(name = "food_id")
//     // private Food food;

//     // Constructors, getters, and setters (Lombok @Data handles most)
//     // Add specific constructors if needed
// }