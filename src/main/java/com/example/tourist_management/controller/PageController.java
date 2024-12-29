package com.example.tourist_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String showHome(Model model) {
        return "static/home";
    }

    @GetMapping("/gallery")
    public String showGallery(Model model) {
        return "static/gallery";
    }

    @GetMapping("/about-sri-lanka")
    public String showAboutSriLanka(Model model) {
        return "static/about-sri-lanka";
    }

    @GetMapping("/faq")
    public String showFaq(Model model) {
        return "static/faq";
    }

    @GetMapping("/about-us")
    public String showAboutUs(Model model) {
        return "static/about-us";
    }
}
