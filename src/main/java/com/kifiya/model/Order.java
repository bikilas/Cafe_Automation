package com.kifiya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    private BigDecimal totalAmount;

    private String status;

    private LocalDateTime createdAt;

    private Long totalItems;

    private Boolean isAvailable;

    private Boolean isVegetarian;

    private Boolean isSeasonal;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String deliveryAddress;

    private String deliveryInstructions;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "order")
    private List<OrderItem> items = new ArrayList<>();
}