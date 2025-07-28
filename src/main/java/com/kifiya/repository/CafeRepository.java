package com.kifiya.repository;

import com.kifiya.model.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime; // Changed from LocalDateTime to LocalTime based on usage in @Query
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Automated Cafe entities,
 * which would include instances like "Kifiya Cafe".
 */
@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    /**
     * Finds an Automated Cafe by its unique name (e.g., "Kifiya Cafe - Bole").
     *
     * @param name The unique name of the automated cafe instance.
     * @return An Optional containing the found Cafe, or empty if no cafe with the given name exists.
     */
    Optional<Cafe> findByName(String name);

    /**
     * Finds cafes by name or location containing the given search terms (case-insensitive).
     *
     * @param name     The name to search for
     * @param location The location to search for
     * @return List of cafes matching the search criteria
     */
    List<Cafe> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String name, String location);

    /**
     * @return A list of operational automated cafes.
     */
    List<Cafe> findByIsOperationalTrue();

    /**
     * Finds all "Kifiya Cafe" instances (or any cafe) that are located in a specific area.
     *
     * @param location The location to search for.
     * @return A list of Cafes found at the specified location.
     */
    List<Cafe> findByLocationContainingIgnoreCase(String location);

    /**
     * Finds operational "Kifiya Cafe" instances (or any cafe) that are scheduled to be open at a specific time.
     *
     * @param currentTime The time to check against the cafe's operating hours.
     * @return A list of automated cafes that are scheduled to be open at the given time.
     */
    @Query("SELECT c FROM Cafe c WHERE c.isOperational = true AND :currentTime BETWEEN c.openingTime AND c.closingTime")
    List<Cafe> findOperationalCafesOpenAtTime(@Param("currentTime") LocalTime currentTime);

    /**
     * Finds "Kifiya Cafe" instances (or any cafe) that have low stock of coffee beans.
     *
     * @param threshold The minimum amount of coffee beans in KG.
     * @return A list of automated cafes with coffee bean stock below the threshold.
     */
    // --- THIS IS THE CRUCIAL CHANGE ---
    // The property in Cafe.java is now 'coffeeBeansKg', so the method name is updated to match.
    List<Cafe> findByCoffeeBeansKgLessThan(int threshold);

    /**
     * Checks if an automated cafe instance with a given name (e.g., "Kifiya Cafe - Bole") already exists.
     *
     * @param name The name to check for existence.
     * @return True if a cafe with the name exists, false otherwise.
     */
    boolean existsByName(String name);

    // You could add specific methods if you expect to frequently filter by "Kifiya Cafe"
    // For example, if you prefix all Kifiya Cafe names:
    // List<Cafe> findByNameStartingWith(String prefix); // e.g., "Kifiya Cafe -"
}