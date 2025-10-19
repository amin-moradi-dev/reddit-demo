package com.example.redditdemo.controller;

import com.example.redditdemo.model.User;
import com.example.redditdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Handles /profile/{id}
    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id)
                .orElse(null); // or throw exception if preferred

        if (user == null) {
            return "error/404"; // Optional: redirect to an error page
        }

        model.addAttribute("user", user);
        return "profile"; // profile.html (your Thymeleaf view)
    }
}
