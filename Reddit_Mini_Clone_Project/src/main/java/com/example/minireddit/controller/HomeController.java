package com.example.minireddit.controller;

import com.example.minireddit.repository.CommunityRepository;
import com.example.minireddit.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CommunityRepository communityRepo;
    private final PostRepository postRepo;

    public HomeController(CommunityRepository communityRepo, PostRepository postRepo) {
        this.communityRepo = communityRepo;
        this.postRepo = postRepo;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("communities", communityRepo.findAll());
        model.addAttribute("posts", postRepo.findAll());
        return "index";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }
}
