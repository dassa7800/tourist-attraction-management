package com.example.tourist_management.controller;

import com.example.tourist_management.model.Attraction;
import com.example.tourist_management.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/attractions")
public class AttractionThymeleafController {

    private final AttractionService attractionService;

    @Autowired
    public AttractionThymeleafController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping
    public String listAttractions(Model model) {
        model.addAttribute("attractions", attractionService.getAllAttractions());
        return "attractions/list";
    }

    @GetMapping("/test")
    public String testAttractions(Model model) {
        model.addAttribute("attractions", attractionService.getAllAttractions());
        return "attractions/test";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("attraction", new Attraction());
        return "attractions/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Attraction attraction = attractionService.getAttractionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attraction ID: " + id));
        model.addAttribute("attraction", attraction);
        return "attractions/form";
    }

    @PostMapping
    public String saveAttraction(@ModelAttribute Attraction attraction) {
        attractionService.saveAttraction(attraction);
        return "redirect:/attractions";
    }

    @GetMapping("/delete/{id}")
    public String deleteAttraction(@PathVariable String id) {
        attractionService.deleteAttractionById(id);
        return "redirect:/attractions";
    }
}
