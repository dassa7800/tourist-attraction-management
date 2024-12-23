package com.example.tourist_management.controller;

import com.example.tourist_management.model.User;
import com.example.tourist_management.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // Inject BCryptPasswordEncoder

    // Display registration form
    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";  // Renders register.html
    }

    // Handle form submission
    @PostMapping
    public String registerUser(@ModelAttribute @Valid User user, BindingResult result, Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "register";  // Return to form with error messages
        }

        // Check if the email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("emailError", "Email is already registered");
            return "register";
        }

        // Encode the password and save user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");  // Default role for new users
        userRepository.save(user);

        return "redirect:/login";  // Redirect to login page after successful registration
    }
}
