package com.kifiya.dto;

import java.util.List;
import lombok.Data;

@Data
public class CafeDto {
    private Long id;
    private String name;
    private String location;
    private String description;
    private String type;
    private String status;
    private Boolean isOperational;
    private Integer capacity;
    private String openingTime;
    private String closingTime;
    private Double averageRating;
    private Integer reviewCount;
    private List<FeatureDto> features;

    public Object map(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'map'");
    }
}
