package com.kifiya.controller;

import com.kifiya.dto.CafeDto;
import com.kifiya.service.CafeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
// import java.util.Map;

@RestController
@RequestMapping("/api/admin/cafes")
public class AdminCafeController {

    @Autowired
    private CafeService cafeService;

    @GetMapping
    public ResponseEntity<?> getAllCafes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            System.out.println("Received request to get all cafes - Page: " + page + ", Size: " + size);
            Page<CafeDto> cafes = cafeService.getAllCafes(page, size);
            System.out.println("Successfully retrieved " + cafes.getNumberOfElements() + " cafes");
            return ResponseEntity.ok(cafes);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid request parameters: " + e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        } catch (Exception e) {
            System.err.println("Error in getAllCafes controller: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An error occurred while fetching cafes: " + e.getMessage()));
        }
    }

     @GetMapping("/{id}")
    public ResponseEntity<CafeDto> getCafeById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Page<CafeDto> cafes = cafeService.getAllCafes(0, 10);
            return ResponseEntity.ok(cafes.getContent().stream()
                    .filter(cafe -> cafe.getId().equals(id))
                    .findFirst()
                    .orElse(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<CafeDto> createCafe(@RequestBody CafeDto cafeDto) {
        if (cafeDto == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            CafeDto createdCafe = cafeService.createCafe(cafeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCafe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CafeDto> updateCafe(
            @PathVariable Long id,
            @RequestBody CafeDto cafeDto) {
        if (id == null || cafeDto == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            CafeDto updatedCafe = cafeService.updateCafe(id, cafeDto);
            return ResponseEntity.ok(updatedCafe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        if (!cafeService.deleteCafe(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateCafeStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        if (id == null || status == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            cafeService.updateCafeStatus(id, status);
            return ResponseEntity.ok("Cafe status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<Object> getCafeStatistics() {
        try {
            return ResponseEntity.ok(cafeService.getCafeStatistics());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CafeDto>> searchCafes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Boolean hasWifi,
            @RequestParam(required = false) Boolean hasParking,
            @RequestParam(required = false) Boolean hasOutdoorSeating) {
        try {
            List<CafeDto> cafes = cafeService.searchCafes(name, location, type, hasWifi, hasParking, hasOutdoorSeating);
            return ResponseEntity.ok(cafes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}