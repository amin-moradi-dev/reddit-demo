package com.example.minireddit.controller;

import com.example.minireddit.model.Post;
import com.example.minireddit.model.User;
import com.example.minireddit.repository.PostRepo;
import com.example.minireddit.repository.UserRepo;
import com.example.minireddit.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private final PostRepo postRepo;
    private final PostService postService;
    private final UserRepo userRepo;

    public PostController(PostRepo postRepo, PostService postService, UserRepo userRepo){
        this.postRepo = postRepo; this.postService = postService; this.userRepo = userRepo;
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model){
        Post p = postRepo.findById(id).orElseThrow();
        List<Post> comments = postRepo.findByParent_IdOrderByCreatedAtAsc(id);
        model.addAttribute("post", p);
        model.addAttribute("comments", comments);
        return "post_detail";
    }

    @PostMapping("/{id}/comment")
    public String comment(@AuthenticationPrincipal UserDetails principal,
                          @PathVariable Long id,
                          @RequestParam String body){
        if (principal == null) return "redirect:/login";
        User me = userRepo.findByUsername(principal.getUsername()).orElseThrow();
        postService.createComment(me, id, body);
        return "redirect:/post/" + id;
    }
}
