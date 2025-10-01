package com.example.minireddit.controller;


import com.example.minireddit.dto.RegisterDto;
import com.example.minireddit.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class AuthController {
    private final UserService users;
    public AuthController(UserService users){ this.users=users; }


    @GetMapping("/login")
    public String login(){ return "auth/login"; }


    @GetMapping("/register")
    public String registerForm(Model model){ model.addAttribute("dto", new RegisterDto("", "", "")); return "auth/register"; }


    @PostMapping("/register")
    public String register(@ModelAttribute("dto") @Valid RegisterDto dto){
        users.register(dto);
        return "redirect:/login?registered";
    }
}