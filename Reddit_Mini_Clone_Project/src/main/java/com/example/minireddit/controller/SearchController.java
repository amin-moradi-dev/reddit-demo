package com.example.minireddit.controller;

import com.example.minireddit.repository.PostRepository;
import com.example.minireddit.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    private final PostRepository postRepo;

    public SearchController(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @GetMapping("/search")
    public String search(@RequestParam String q, Model model) {
        List<Post> posts = postRepo.findAll().stream()
                .filter(p -> p.getTitle().toLowerCase().contains(q.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("query", q);
        model.addAttribute("posts", posts);
        return "search";
    }
}
