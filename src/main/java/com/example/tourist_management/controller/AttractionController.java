package com.example.tourist_management.controller;

import com.example.tourist_management.model.Attraction;
import com.example.tourist_management.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    // Create or Update an Attraction
    @PostMapping
    public ResponseEntity<Attraction> createOrUpdateAttraction(@RequestBody Attraction attraction) {
        return ResponseEntity.ok(attractionService.saveAttraction(attraction));
    }

    // Get all Attractions
    @GetMapping
    public ResponseEntity<List<Attraction>> getAllAttractions() {
        return ResponseEntity.ok(attractionService.getAllAttractions());
    }

    // Get an Attraction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Attraction> getAttractionById(@PathVariable String id) {
        Optional<Attraction> attraction = attractionService.getAttractionById(id);
        return attraction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete an Attraction by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttractionById(@PathVariable String id) {
        attractionService.deleteAttractionById(id);
        return ResponseEntity.noContent().build();
    }
}
