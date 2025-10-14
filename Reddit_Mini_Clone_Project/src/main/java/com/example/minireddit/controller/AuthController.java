package com.example.minireddit.controller;

import com.example.minireddit.model.User;
import com.example.minireddit.model.VerificationToken;
import com.example.minireddit.repository.VerificationTokenRepository;
import com.example.minireddit.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;
    private final VerificationTokenRepository tokenRepo;

    public AuthController(UserService userService, VerificationTokenRepository tokenRepo) {
        this.userService = userService;
        this.tokenRepo = tokenRepo;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String fullName,
                               @RequestParam String email,
                               @RequestParam String password,
                               RedirectAttributes redirectAttributes) {
        try {
            userService.register(fullName, email, password);
            redirectAttributes.addFlashAttribute("success", "✅ Account created successfully! Check your email to activate it.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ " + e.getMessage());
            return "redirect:/register";
        }
    }

    @GetMapping("/activate/{token}")
    public String activate(@PathVariable String token, RedirectAttributes redirectAttributes) {
        VerificationToken t = tokenRepo.findById(token).orElse(null);

        if (t == null || t.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("error", "⚠️ Invalid or expired activation link.");
        } else {
            User u = t.getUser();
            userService.enableUser(u.getId());
            redirectAttributes.addFlashAttribute("success", "🎉 Account activated! You can now log in.");
        }

        return "redirect:/login";
    }
}
