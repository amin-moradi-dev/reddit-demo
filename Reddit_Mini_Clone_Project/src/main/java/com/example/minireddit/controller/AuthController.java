package com.example.minireddit.controller;

import com.example.minireddit.service.UserService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService){this.userService=userService;}

    @GetMapping("/login")
    public String login(){ return "login"; }

    @GetMapping("/register")
    public String registerForm(){ return "register"; }

    @PostMapping("/register")
    public String register(@RequestParam @Email String email,
                           @RequestParam @NotBlank String username,
                           @RequestParam @NotBlank String fullName,
                           @RequestParam @NotBlank String password,
                           Model model){
        try {
            userService.register(email, username, fullName, password);
            return "redirect:/login?registered";
        } catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}

