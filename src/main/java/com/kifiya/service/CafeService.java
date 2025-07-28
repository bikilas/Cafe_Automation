// src/main/java/com/kifiya/service/CafeService.java
package com.kifiya.service;

import com.kifiya.dto.CafeDto;
import com.kifiya.exception.CafeNotFoundException;
import com.kifiya.exception.InvalidCafeStatusException;
import com.kifiya.model.Cafe;
import com.kifiya.repository.CafeRepository;

// import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.kifiya.exception.ResourceNotFoundException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CafeService {
    @Autowired
    private CafeRepository cafeRepository;



    private boolean isValidStatus(String status) {
        return Arrays.asList("ACTIVE", "INACTIVE", "CLOSED").contains(status.toUpperCase());
    }

    private CafeDto convertToDto(Cafe cafe) {
        if (cafe == null) {
            return null;
        }
        try {
            CafeDto dto = new CafeDto();
            dto.setId(cafe.getId());
            dto.setName(cafe.getName() != null ? cafe.getName() : "");
            dto.setLocation(cafe.getLocation() != null ? cafe.getLocation() : "");
            dto.setDescription(cafe.getDescription() != null ? cafe.getDescription() : "");
            dto.setType(cafe.getType() != null ? cafe.getType() : "");
            dto.setStatus(cafe.getStatus() != null ? cafe.getStatus() : "ACTIVE");
            dto.setIsOperational(cafe.getIsOperational() != null ? cafe.getIsOperational() : true);
            dto.setCapacity(cafe.getCapacity() != null ? cafe.getCapacity() : 0);
            dto.setOpeningTime(cafe.getOpeningTime() != null ? cafe.getOpeningTime().toString() : "");
            dto.setClosingTime(cafe.getClosingTime() != null ? cafe.getClosingTime().toString() : "");
            
            // Set default values for additional fields if they exist in Cafe entity
            if (cafe.getAverageRating() != null) {
                dto.setAverageRating(cafe.getAverageRating());
            }
            if (cafe.getReviewCount() != null) {
                dto.setReviewCount(cafe.getReviewCount());
            }
            
            return dto;
        } catch (Exception e) {
            System.err.println("Error converting cafe to DTO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error converting cafe data. " + e.getMessage(), e);
        }
    }

    @Transactional
    public CafeDto createCafe(CafeDto cafeDto) {
        // Input validation
        if (cafeDto == null) {
            throw new IllegalArgumentException("Cafe data cannot be null");
        }
        
        // Required field validation
        if (cafeDto.getName() == null || cafeDto.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Cafe name is required");
        }
        
        if (cafeDto.getLocation() == null || cafeDto.getLocation().trim().isEmpty()) {
            throw new IllegalArgumentException("Location is required");
        }

        try {
            // Create new cafe entity
            Cafe cafe = new Cafe();
            cafe.setName(cafeDto.getName().trim());
            cafe.setLocation(cafeDto.getLocation().trim());
            
            // Handle optional fields with defaults
            cafe.setDescription(cafeDto.getDescription() != null ? cafeDto.getDescription().trim() : "");
            cafe.setType(cafeDto.getType() != null ? cafeDto.getType() : "CAFE");
            cafe.setStatus(cafeDto.getStatus() != null ? cafeDto.getStatus() : "ACTIVE");
            cafe.setIsOperational(cafeDto.getIsOperational() != null ? cafeDto.getIsOperational() : true);
            cafe.setCapacity(cafeDto.getCapacity() != null ? cafeDto.getCapacity() : 0);
            
            // Handle timestamps - make them optional with defaults
            LocalDateTime now = LocalDateTime.now();
            cafe.setOpeningTime(now);
            cafe.setClosingTime(now.plusHours(8));
            
            // Save the cafe
            Cafe savedCafe = cafeRepository.save(cafe);
            System.out.println("Successfully saved cafe with ID: " + savedCafe.getId());
            
            return convertToDto(savedCafe);
            
        } catch (Exception e) {
            System.err.println("Error in createCafe: " + e.getMessage());
            throw new RuntimeException("Failed to create cafe: " + e.getMessage(), e);
        }
    }

    @Transactional
    public CafeDto updateCafe(Long id, CafeDto cafeDto) {
        if (id == null || cafeDto == null) {
            throw new IllegalArgumentException("Id and CafeDto cannot be null");
        }

        return cafeRepository.findById(id)
                .map(existingCafe -> {
                    // Update fields from DTO
                    if (cafeDto.getName() != null) {
                        existingCafe.setName(cafeDto.getName());
                    }
                    if (cafeDto.getLocation() != null) {
                        existingCafe.setLocation(cafeDto.getLocation());
                    }
                    if (cafeDto.getDescription() != null) {
                        existingCafe.setDescription(cafeDto.getDescription());
                    }
                    if (cafeDto.getType() != null) {
                        existingCafe.setType(cafeDto.getType());
                    }
                    if (cafeDto.getStatus() != null) {
                        if (!isValidStatus(cafeDto.getStatus())) {
                            throw new InvalidCafeStatusException("Invalid cafe status");
                        }
                        existingCafe.setStatus(cafeDto.getStatus());
                    }
                    if (cafeDto.getIsOperational() != null) {
                        existingCafe.setIsOperational(cafeDto.getIsOperational());
                    }
                    if (cafeDto.getCapacity() != null) {
                        existingCafe.setCapacity(cafeDto.getCapacity());
                    }
                    if (cafeDto.getOpeningTime() != null) {
                        existingCafe.setOpeningTime(LocalDateTime.parse(cafeDto.getOpeningTime()));
                    }
                    if (cafeDto.getClosingTime() != null) {
                        existingCafe.setClosingTime(LocalDateTime.parse(cafeDto.getClosingTime()));
                    }

                    // Save and convert to DTO
                    Cafe updatedCafe = cafeRepository.save(existingCafe);
                    return convertToDto(updatedCafe);
                })
                .orElseThrow(() -> new CafeNotFoundException("Cafe not found with id: " + id));
    }

    public Object getCafeStatistics() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCafeStatistics'");
    }

    /**
     * Get a cafe by its ID
     * @param id the ID of the cafe to retrieve
     * @return the Cafe entity
     * @throws ResourceNotFoundException if no cafe is found with the given ID
     */
    public Cafe getCafeById(Long id) {
        return cafeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cafe not found with id: " + id));
    }
    
    /**
     * Get a cafe DTO by its ID with pagination support
     * @param id the ID of the cafe to retrieve
     * @param pageable pagination information
     * @return a page of CafeDto
     */
    public Page<CafeDto> getCafeDtoById(Long id, Pageable pageable) {
        Cafe cafe = getCafeById(id);
        return new PageImpl<>(List.of(convertToDto(cafe)), pageable, 1);
    }

    public List<CafeDto> searchCafes(String name, String location, String type, Boolean hasWifi, Boolean hasParking,
                                     Boolean hasOutdoorSeating) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchCafes'");
    }

    public boolean deleteCafe(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCafe'");
    }

    @Transactional(readOnly = true)
    public Page<CafeDto> getAllCafes(int page, int size) {
        // Add input validation
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        }
        if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one");
        }
        
        try {
            // Log the request
            System.out.println("Fetching cafes - Page: " + page + ", Size: " + size);
            
            // Get paginated cafes and convert to DTOs
            Page<Cafe> cafes = cafeRepository.findAll(PageRequest.of(page, size));
            
            // Log the result
            System.out.println("Found " + cafes.getTotalElements() + " cafes in total");
            
            // Convert each Cafe to CafeDto, handling any conversion errors
            List<CafeDto> cafeDtos = cafes.getContent().stream()
                .map(cafe -> {
                    try {
                        return convertToDto(cafe);
                    } catch (Exception e) {
                        System.err.println("Error converting cafe with ID " + cafe.getId() + " to DTO: " + e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
                
            // Create a new Page with the converted DTOs
            return new PageImpl<>(
                cafeDtos,
                PageRequest.of(page, size, cafes.getSort()),
                cafes.getTotalElements()
            );
            
        } catch (Exception e) {
            // Log the full error with stack trace
            System.err.println("Error in getAllCafes: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Unable to fetch cafes. Please try again later.", e);
        }
    }

    public void updateCafeStatus(Long id, String status) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCafeStatus'");
    }

    public CafeDto toggleOperationalStatus(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toggleOperationalStatus'");
    }
}