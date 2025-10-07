package com.example.minireddit.controller;

import com.example.minireddit.model.Community;
import com.example.minireddit.model.Post;
import com.example.minireddit.repository.CommunityRepo;
import com.example.minireddit.repository.PostRepo;
import com.example.minireddit.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/community")
public class CommunityController {
    private final CommunityRepo communityRepo;
    private final PostRepo postRepo;
    private final PostService postService;

    public CommunityController(CommunityRepo communityRepo, PostRepo postRepo, PostService postService){
        this.communityRepo = communityRepo; this.postRepo = postRepo; this.postService = postService;
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model){
        Community c = communityRepo.findById(id).orElseThrow();
        List<Post> posts = postRepo.findByCommunity_IdOrderByCreatedAtDesc(id);
        model.addAttribute("community", c);
        model.addAttribute("posts", posts);
        return "community";
    }

    @GetMapping("/{id}/newpost")
    public String newPostForm(@PathVariable Long id, Model model){
        model.addAttribute("communityId", id);
        return "new_post";
    }

    @PostMapping("/{id}/newpost")
    public String create(@AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
                         @PathVariable Long id,
                         @RequestParam String title,
                         @RequestParam String body,
                         @RequestParam(required=false) String imageUrl){
        // Minimal user lookup skipped for brevity; in a real app inject UserRepo and load by username.
        // Here we fake it by placing username in model; actual mapping should be added if needed.
        // For this starter, we just redirect; implement real mapping in your project.
        return "redirect:/community/" + id;
    }
}
