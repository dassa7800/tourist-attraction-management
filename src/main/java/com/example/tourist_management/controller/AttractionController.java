package com.example.tourist_management.controller;

import com.example.tourist_management.model.Attraction;
import com.example.tourist_management.model.Review;
import com.example.tourist_management.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing tourist attractions.
 * Provides endpoints to perform CRUD operations on attractions.
 */
@Controller
@RequestMapping("/attractions")  // Base URL for all endpoints in this controller
public class AttractionController {

    private final AttractionService attractionService;

    /**
     * Constructor for AttractionController with dependency injection of AttractionService.
     *
     * @param attractionService Service handling business logic for attractions.
     */

    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }


    /**
     * Endpoint to retrieve all attractions from the database.
     *
     * @return ResponseEntity with a list of all attractions.
     */
    /*@GetMapping
    public String showAttractions(Model model) {
        List<Attraction> attractions = attractionService.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractions/list";  // Refers to templates/attractions/list.html
    }
    */
    @GetMapping("/admin")
    public String showAttractions(Model model) {
        List<Attraction> attractions = attractionService.getAllAttractions();
        // Calculate average ratings for each attraction
        attractions.forEach(attraction -> {
            double avgRating = attractionService.calculateAverageRating(attraction.getId());
            attraction.setAverageRating(avgRating);
        });
        model.addAttribute("attractions", attractions);
        return "attractions/list";  // Refers to templates/attractions/list.html
    }

    @GetMapping("/view")
    public String showUserAttractions(Model model) {
        List<Attraction> attractions = attractionService.getAllAttractions();
        attractions.forEach(attraction -> {
            double avgRating = attractionService.calculateAverageRating(attraction.getId());
            attraction.setAverageRating(avgRating);
        });
        model.addAttribute("attractions", attractions);
        return "attractions/list-user";  // New template for user-only view
    }

    // Display form for new attraction
    @GetMapping("/admin/new")
    public String showNewAttractionForm(Model model) {
        model.addAttribute("attraction", new Attraction());
        return "attractions/form";  // Returns form.html for creating new attraction
    }

    // Handle form submission for new or updated attraction
    @PostMapping("/admin/save")
    public String saveAttraction(@ModelAttribute("attraction") Attraction attraction) {
        if (attraction.getId() != null && attraction.getId().isEmpty()){
            attraction.setId(null); // Force new ID for new attractions
        }
        attractionService.saveAttraction(attraction);
        return "redirect:/attractions/admin";  // Redirect to attractions list
    }
    /**
     * Endpoint to fetch details of a specific attraction by its ID.
     *
     * @param id The unique identifier of the attraction (path variable).
     * @return ResponseEntity with the attraction details if found, or 404 if not found.
     */

    // Display form for editing existing attraction
    @GetMapping("/admin/edit/{id}")
    public String showEditAttractionForm(@PathVariable String id, Model model) {
        Optional<Attraction> attraction = attractionService.getAttractionById(id);
        if (attraction.isPresent()) {
            model.addAttribute("attraction", attraction.get());
            return "attractions/form";  // Reuse form.html for editing
        } else {
            return "redirect:/attractions/admin";  // Redirect if attraction not found
        }
    }
    /**
     * Endpoint to delete an attraction by its ID.
     *
     * @param id The ID of the attraction to be deleted.
     * @return ResponseEntity with no content (204) after successful deletion.
     */
    // Delete attraction by ID
    @GetMapping("/admin/delete/{id}")
    public String deleteAttraction(@PathVariable String id) {
        attractionService.deleteAttractionById(id);
        return "redirect:/attractions/admin";  // Redirect to list after deletion
    }

    //Reviews and details in details page for users
    @GetMapping("/details/{id}")
    public String viewAttraction(@PathVariable String id, Model model) {
        Optional<Attraction> attraction = attractionService.getAttractionById(id);
        if (attraction.isPresent()) {
            Attraction detailedAttraction = attraction.get();
            double avgRating = attractionService.calculateAverageRating(id);
            detailedAttraction.setAverageRating(avgRating);
            model.addAttribute("attraction", detailedAttraction);
            return "attractions/details";
        }
        return "redirect:/attractions/view";
    }

    //Reviews and details page for admin
    @GetMapping("/admin/details/{id}")
    public String viewadminAttraction(@PathVariable String id, Model model) {
        Optional<Attraction> attraction = attractionService.getAttractionById(id);
        if (attraction.isPresent()) {
            Attraction detailedAttraction = attraction.get();
            double avgRating = attractionService.calculateAverageRating(id);
            detailedAttraction.setAverageRating(avgRating);
            model.addAttribute("attraction", detailedAttraction);
            return "attractions/details-admin";
        }
        return "redirect:/attractions/admin";
    }
    @PostMapping("/review")
    public String addReview(@RequestParam String id,
                            @RequestParam String user,
                            @RequestParam String comment,
                            @RequestParam double rating) {
        Review review = new Review(user, comment, rating);
        attractionService.addReview(id, review);
        return "redirect:/attractions/details/" + id;
    }


}
