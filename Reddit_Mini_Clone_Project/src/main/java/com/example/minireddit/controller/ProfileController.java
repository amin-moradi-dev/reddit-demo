package com.example.minireddit.controller;

import com.example.minireddit.model.User;
import com.example.minireddit.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    private final UserRepository userRepo;

    public ProfileController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable Long id, Model model) {
        User u = userRepo.findById(id).orElse(null);
        if (u == null) return "redirect:/";
        model.addAttribute("user", u);
        return "profile";
    }

    @GetMapping("/myprofile")
    public String myProfile(@AuthenticationPrincipal UserDetails details) {
        if (details == null) return "redirect:/login";
        User u = userRepo.findByEmail(details.getUsername()).orElse(null);
        return (u == null) ? "redirect:/login" : "redirect:/profile/" + u.getId();
    }
}

