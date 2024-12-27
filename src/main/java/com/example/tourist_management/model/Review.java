// Review.java
package com.example.tourist_management.model;

public class Review {
    private String user;
    private String comment;
    private double rating;

    public Review(String user, String comment, double rating) {
        this.user = user;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters and Setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
