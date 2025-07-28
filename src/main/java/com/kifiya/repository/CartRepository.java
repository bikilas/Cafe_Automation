package com.kifiya.repository; // A common convention for repository packages

import com.kifiya.model.Cart; // Import your Cart entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this interface as a Spring Data JPA repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    // JpaRepository<YourEntity, TypeOfYourEntityId>

    // This interface automatically inherits basic CRUD (Create, Read, Update, Delete)
    // operations for the Cart entity, such as:
    // - save(Cart cart)
    // - findById(Long id)
    // - findAll()
    // - delete(Cart cart)
    // - count()
    // etc.

    // No custom methods are added here because the provided Cart model
    // does not expose specific fields like 'customer' or 'status' for querying.
}