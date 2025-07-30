package com.kifiya.dto;

import com.kifiya.model.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private BigDecimal totalAmount;
    private String status;
    private Long totalItems;
    private Boolean isAvailable;
    private Boolean isVegetarian;
    private Boolean isSeasonal;
    private PaymentMethod paymentMethod;
    private String deliveryAddress;
    private String deliveryInstructions;
    private List<OrderItemRequest> items;
    private Long cafeId;

    @Data
    public static class OrderItemRequest {
        private Long foodId;
        private String foodName;
        private BigDecimal price;
        private Integer quantity;
        private String notes;
    }
}
