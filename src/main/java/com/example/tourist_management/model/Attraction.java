package com.example.tourist_management.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Transient;  // For Spring Data (MongoDB)

import java.util.List;

@Document(collection = "Attractions") // Maps to the 'attractions' collection in MongoDB
public class Attraction {
    @Id
    private String id; // _id field in MongoDB

    private String name;
    private String location;
    private String description;
    private double entryFee;
    private String type;
    private double rating;
    private List<String> images;
    private List<Review> reviews;  // field for reviews

    @Transient
    private double averageRating = 0.0;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(double entryFee) {
        this.entryFee = entryFee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    // Getters and Setters for reviews
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public void calculateAverageRating() {
        if (reviews != null && !reviews.isEmpty()) {
            this.averageRating = reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
        } else {
            this.averageRating = 0.0;
        }
    }
}
