package com.example.minireddit.controller;

import com.example.minireddit.model.Post;
import com.example.minireddit.model.User;
import com.example.minireddit.repository.PostRepository;
import com.example.minireddit.repository.UserRepository;
import com.example.minireddit.service.VoteService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/post")
public class VoteApiController {

    private final VoteService voteService;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public VoteApiController(VoteService voteService, PostRepository postRepo, UserRepository userRepo) {
        this.voteService = voteService;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/{id}/upvote")
    public Map<String, Long> upvote(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return handleVote(id, userDetails, 1);
    }

    @PostMapping("/{id}/downvote")
    public Map<String, Long> downvote(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return handleVote(id, userDetails, -1);
    }

    private Map<String, Long> handleVote(Long id, UserDetails userDetails, int value) {
        Post post = postRepo.findById(id).orElse(null);
        if (post == null) return Map.of("upvotes", 0L, "downvotes", 0L);
        User u = userRepo.findByEmail(userDetails.getUsername()).orElse(null);
        voteService.vote(u, post, value);
        return Map.of(
                "upvotes", voteService.countUpvotes(post),
                "downvotes", voteService.countDownvotes(post)
        );
    }
}
