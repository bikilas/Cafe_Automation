package com.kifiya.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDto {
    
    private Long id;
    private Long orderId;
    private Long foodId;
    private String foodName;
    private BigDecimal price;
    private int quantity;
    private BigDecimal subtotal;
    private String notes;
}
