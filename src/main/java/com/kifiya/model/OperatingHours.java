package com.kifiya.model;

import java.util.Map;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperatingHours {
    @Column(name = "opening_time")
    private String openingTime;

    @Column(name = "closing_time")
    private String closingTime;

    @ElementCollection
    @CollectionTable(name = "operating_hours_days", joinColumns = @JoinColumn(name = "cafe_id"))
    @MapKeyColumn(name = "day")
    @Column(name = "hours")
    private Map<String, String> hoursByDay;
}
