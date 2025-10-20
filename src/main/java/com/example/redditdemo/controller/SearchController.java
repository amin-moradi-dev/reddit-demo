package com.example.redditdemo.controller;

import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.Community;
import com.example.redditdemo.repository.PostRepository;
import com.example.redditdemo.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommunityRepository communityRepository;

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {
        // Search posts
        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(query);

        // Search communities
        List<Community> communities = communityRepository.findByNameContainingIgnoreCase(query);

        // Add results to model
        model.addAttribute("query", query);
        model.addAttribute("posts", posts);
        model.addAttribute("communities", communities);

        return "search-results";
    }
}
