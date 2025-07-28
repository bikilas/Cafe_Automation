package com.kifiya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private Long userId;
    private Long cafeId;
    private BigDecimal totalAmount; // Changed from Long to BigDecimal
    private String status;
    private LocalDateTime createdAt;
    private Long totalItems;
    private Boolean isAvailable;
    private Boolean isVegetarian;
    private Boolean isSeasonal;
    private String paymentMethod;
    private String deliveryAddress;
    private String deliveryInstructions;
    private List<OrderItemDto> items;
}