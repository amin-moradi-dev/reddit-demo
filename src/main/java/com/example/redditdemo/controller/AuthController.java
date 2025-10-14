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

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }
        try {
            userService.registerUser(user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
        return "redirect:/login?success";
    }



    @GetMapping("/login")
    public String showLogin(@RequestParam(required = false) String success,
                            Model model) {

        if (success != null) {
            model.addAttribute("successMessage", "Registration successful! Please login.");
        }

        return "auth/login";
    }
}
