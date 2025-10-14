package com.example.minireddit.controller;

import com.example.minireddit.model.Role;
import com.example.minireddit.model.User;
import com.example.minireddit.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepo;

    public AdminController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepo.findAll());
        return "admin_users";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userRepo.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "User deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete user: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/toggleAdmin")
    public String toggleAdmin(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userRepo.findById(id).ifPresent(u -> {
            Set<Role> roles = u.getRoles();
            if (roles.contains(Role.ADMIN)) {
                roles.remove(Role.ADMIN);
                redirectAttributes.addFlashAttribute("info", "Admin role removed for user " + u.getEmail());
            } else {
                roles.add(Role.ADMIN);
                redirectAttributes.addFlashAttribute("success", "User " + u.getEmail() + " is now an Admin!");
            }
            userRepo.save(u);
        });
        return "redirect:/admin/users";
    }
}
