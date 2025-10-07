package com.example.minireddit.controller;

import com.example.minireddit.model.Role;
import com.example.minireddit.model.User;
import com.example.minireddit.repository.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {
    private final UserRepo userRepo;
    public AdminUserController(UserRepo userRepo){this.userRepo=userRepo;}

    @GetMapping
    public String list(Model model){
        model.addAttribute("users", userRepo.findAll());
        return "admin_users";
    }

    @PostMapping("/{id}/promote")
    public String promote(@PathVariable Long id){
        User u = userRepo.findById(id).orElseThrow();
        u.setRole(Role.ADMIN);
        userRepo.save(u);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/demote")
    public String demote(@PathVariable Long id){
        User u = userRepo.findById(id).orElseThrow();
        u.setRole(Role.USER);
        userRepo.save(u);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        userRepo.deleteById(id);
        return "redirect:/admin/users";
    }
}

