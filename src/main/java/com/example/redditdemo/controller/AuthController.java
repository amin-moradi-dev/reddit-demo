package com.example.redditdemo.controller;

import com.example.redditdemo.model.User;
import com.example.redditdemo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Show registration form
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult,
                           Model model) {

        // If form validation fails, show the form again
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        try {
            // Check if username is already taken
            if (userService.getUserByUsername(user.getUsername()).isPresent()) {
                model.addAttribute("error", "Username already exists");
                return "auth/register";
            }

            // Check if email is already registered
            if (userService.getUserByEmail(user.getEmail()).isPresent()) {
                model.addAttribute("error", "Email already exists");
                return "auth/register";
            }

            // Save the new user (password will be encoded)
            userService.saveUser(user);

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }

        // Redirect to login page with success message
        return "redirect:/home?registerSuccess";

    }

    // Show login form
    @GetMapping("/login")
    public String showLogin(@RequestParam(required = false) String success,
                            Model model) {

        // Add a fresh user object for the form
        model.addAttribute("user", new User());

        if (success != null) {
            model.addAttribute("successMessage", "Registration successful! Please login.");
        }

        return "auth/login";
    }

}
