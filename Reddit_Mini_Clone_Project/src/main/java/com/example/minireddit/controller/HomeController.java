package com.example.minireddit.controller;

import com.example.minireddit.model.Post;
import com.example.minireddit.repository.PostRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {
    private final PostRepo postRepo;
    public HomeController(PostRepo postRepo){this.postRepo = postRepo;}

    @GetMapping({"/","/search"})
    public String index(@RequestParam(value="q", required=false) String q, Model model){
        List<Post> posts = (q==null || q.isBlank()) ? postRepo.findAll() : postRepo.findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(q);
        model.addAttribute("posts", posts);
        model.addAttribute("q", q);
        return "index";
    }
}

