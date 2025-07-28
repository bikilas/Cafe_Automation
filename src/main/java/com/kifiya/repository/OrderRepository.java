package com.kifiya.repository;

import com.kifiya.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    // Find orders by user
    List<Order> findByUserId(Long userId);
    
    // Find orders by cafe
    List<Order> findByCafeId(Long cafeId);
    
    // Find orders by status
    List<Order> findByStatus(String status);
    
    // Find orders by date range
    List<Order> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // Find recent orders
    List<Order> findByCreatedAtGreaterThanOrderByCreatedAtDesc(LocalDateTime date);
}
