package com.example.redditdemo.controller;

import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.User;
import com.example.redditdemo.repository.PostRepository;
import com.example.redditdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public String createPost(@ModelAttribute Post newPost,
                             @AuthenticationPrincipal UserDetails currentUser) {

        User author = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        newPost.setAuthor(author);

        postRepository.save(newPost);

        return "redirect:/";
    }
}
