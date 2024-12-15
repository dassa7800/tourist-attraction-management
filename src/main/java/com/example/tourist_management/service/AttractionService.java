package com.example.tourist_management.service;

import com.example.tourist_management.model.Attraction;
import com.example.tourist_management.repository.AttractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttractionService {

    private final AttractionRepository attractionRepository;

    @Autowired
    public AttractionService(AttractionRepository attractionRepository) {
        this.attractionRepository = attractionRepository;
    }

    // Create or Update an Attraction
    public Attraction saveAttraction(Attraction attraction) {
        return attractionRepository.save(attraction);
    }

    // Get all Attractions
    public List<Attraction> getAllAttractions() {
        return attractionRepository.findAll();
    }

    // Get an Attraction by ID
    public Optional<Attraction> getAttractionById(String id) {
        return attractionRepository.findById(id);
    }

    // Delete an Attraction by ID
    public void deleteAttractionById(String id) {
        attractionRepository.deleteById(id);
    }
}
