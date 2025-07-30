package com.kifiya.controller;

import com.kifiya.dto.DrinkDto;
import com.kifiya.service.DrinkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/drinks")
@RequiredArgsConstructor
public class DrinkController {
    private final DrinkService drinkService;

    @GetMapping
    public ResponseEntity<List<DrinkDto>> getAllDrinks() {
        return ResponseEntity.ok(drinkService.getAllDrinks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrinkDto> getDrinkById(@PathVariable Long id) {
        return ResponseEntity.ok(drinkService.getDrinkById(id));
    }

    @PostMapping
    public ResponseEntity<DrinkDto> createDrink(@Valid @RequestBody DrinkDto drinkDto) {
        DrinkDto created = drinkService.createDrink(drinkDto);
        return ResponseEntity
                .created(URI.create("/api/drinks/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DrinkDto> updateDrink(
            @PathVariable Long id, 
            @Valid @RequestBody DrinkDto drinkDto) {
        return ResponseEntity.ok(drinkService.updateDrink(id, drinkDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable Long id) {
        drinkService.deleteDrink(id);
        return ResponseEntity.noContent().build();
    }
}