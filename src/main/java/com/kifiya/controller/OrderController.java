package com.kifiya.controller;

import com.kifiya.dto.OrderDto;
import com.kifiya.dto.OrderRequest;
import com.kifiya.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    // Get all orders (admin only)
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    // Get order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    
    // Create new order
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        try {
            // Create new OrderDto and map all fields from OrderRequest
            OrderDto orderDto = new OrderDto();
            
            // Required fields
            if (orderRequest.getUserId() == null || orderRequest.getCafeId() == null || 
                orderRequest.getTotalAmount() == null || orderRequest.getPaymentMethod() == null) {
                return ResponseEntity.badRequest().body("Missing required fields: userId, cafeId, totalAmount, and paymentMethod are required");
            }
            
            orderDto.setUserId(orderRequest.getUserId());
            orderDto.setCafeId(orderRequest.getCafeId());
            orderDto.setTotalAmount(orderRequest.getTotalAmount());
            orderDto.setPaymentMethod(orderRequest.getPaymentMethod());
            
            // Optional fields with null checks and default values
            orderDto.setStatus(orderRequest.getStatus() != null ? orderRequest.getStatus() : "PENDING");
            orderDto.setTotalItems(orderRequest.getTotalItems() != null ? orderRequest.getTotalItems() : 0L);
            orderDto.setIsAvailable(orderRequest.getIsAvailable() != null ? orderRequest.getIsAvailable() : true);
            orderDto.setIsVegetarian(orderRequest.getIsVegetarian() != null ? orderRequest.getIsVegetarian() : false);
            orderDto.setIsSeasonal(orderRequest.getIsSeasonal() != null ? orderRequest.getIsSeasonal() : false);
            
            if (orderRequest.getDeliveryAddress() != null) {
                orderDto.setDeliveryAddress(orderRequest.getDeliveryAddress());
            }
            if (orderRequest.getDeliveryInstructions() != null) {
                orderDto.setDeliveryInstructions(orderRequest.getDeliveryInstructions());
            }
            
            // Create the order using the service
            OrderDto createdOrder = orderService.createOrder(orderDto);
            return new ResponseEntity<>(createdOrder, org.springframework.http.HttpStatus.CREATED);
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error creating order: " + e.getMessage());
        }
    }
    
    // Update order
    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }
    
    // Delete order
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
    // Get orders by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderDto> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }
    
    // Get orders by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDto>> getOrdersByStatus(@PathVariable String status) {
        List<OrderDto> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
}