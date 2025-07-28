package com.kifiya.controller;

import com.kifiya.dto.CafeDto;
import com.kifiya.service.CafeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cafes")
public class CafeController {

    @Autowired
    private CafeService cafeService;

    @GetMapping
    public ResponseEntity<Page<CafeDto>> getAllCafes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CafeDto> cafes = cafeService.getAllCafes(page, size);
        return ResponseEntity.ok(cafes);
    }

    @PostMapping
    public ResponseEntity<?> createCafe(@RequestBody CafeDto cafeDto) {
        try {
            // Log the incoming request
            System.out.println("Received create cafe request: " + cafeDto);
            
            // Validate required fields
            if (cafeDto == null) {
                return ResponseEntity.badRequest().body("Cafe data is required");
            }
            
            if (cafeDto.getName() == null || cafeDto.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Cafe name is required");
            }
            
            if (cafeDto.getLocation() == null || cafeDto.getLocation().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Location is required");
            }
            
            // Set default values if needed
            if (cafeDto.getStatus() == null) {
                cafeDto.setStatus("ACTIVE");
            }
            if (cafeDto.getIsOperational() == null) {
                cafeDto.setIsOperational(true);
            }
            
            try {
                CafeDto createdCafe = cafeService.createCafe(cafeDto);
                System.out.println("Successfully created cafe: " + createdCafe.getId());
                return ResponseEntity.status(HttpStatus.CREATED).body(createdCafe);
            } catch (IllegalArgumentException e) {
                System.err.println("Validation error creating cafe: " + e.getMessage());
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (Exception e) {
                System.err.println("Error creating cafe: " + e.getMessage());
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error creating cafe: " + e.getMessage());
            }
                
        } catch (Exception e) {
            System.err.println("Unexpected error in createCafe: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred. Please try again later.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id) {
        try {
            boolean deleted = cafeService.deleteCafe(id);
            if (!deleted) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.noContent().build();
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of());
        }
    }
}