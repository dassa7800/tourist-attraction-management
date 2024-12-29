package com.example.tourist_management.service;

import com.example.tourist_management.model.Attraction;
import com.example.tourist_management.repository.AttractionRepository;
import com.example.tourist_management.model.Review; // refering the review class
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        // If the attraction has no ID, create a new one
        if (attraction.getId() == null || attraction.getId().isEmpty()) {
            attraction.setId(null);  // Ensure ID is null for new attractions
        }
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

    //Update and add reviews
    public void addReview(String attractionId, Review review) {
        Attraction attraction = attractionRepository.findById(attractionId).orElseThrow(() -> new RuntimeException("Attraction not found"));
        if (attraction.getReviews() == null) {
            attraction.setReviews(new ArrayList<>());
        }
        attraction.getReviews().add(review);

        // Recalculate average rating after adding a new review
        attraction.calculateAverageRating();
        attractionRepository.save(attraction);
    }

    // Calculate the average rating for each attraction
    public double calculateAverageRating(String attractionId) {
        Attraction attraction = attractionRepository.findById(attractionId)
                .orElseThrow(() -> new RuntimeException("Attraction not found"));

        if (attraction.getReviews() == null || attraction.getReviews().isEmpty()) {
            return 0.0;
        }

        return attraction.getReviews().stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }

}
