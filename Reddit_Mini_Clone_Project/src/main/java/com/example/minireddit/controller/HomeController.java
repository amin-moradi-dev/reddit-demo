package com.example.minireddit.controller;

import com.example.minireddit.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.minireddit.model.Post;


@Controller
public class HomeController {
    private final PostService posts;
    public HomeController(PostService posts){ this.posts=posts; }


    @GetMapping("/")
    public String home(@RequestParam(defaultValue="0") int page, Model model){
        Page<Post> feed = posts.home(page, 10);
        model.addAttribute("feed", feed);
        return "index";
    }
}
