package com.kifiya.model; // Or wherever your model classes are

import jakarta.persistence.*;
import lombok.Data; // Assuming Lombok is used

@Entity
@Data
@Table(name = "tbl_transaction_type") // Important: define the actual table name
public class TblTransactionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String typeName; // e.g., "Purchase", "Refund", "Cancellation"

    // You might add other fields like description, etc.

    // Getters and Setters (Lombok @Data handles this)
}