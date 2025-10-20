package com.example.redditdemo.controller;

import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.VoteType;
import com.example.redditdemo.repository.PostRepository;
import com.example.redditdemo.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController // Use RestController for JSON responses
@RequestMapping("/posts")
@RequiredArgsConstructor
public class VoteController {

    private final VoteService voteService;
    private final PostRepository postRepository;

    @PostMapping("/{postId}/upvote")
    public ResponseEntity<?> upvote(@PathVariable Long postId, Principal principal) {
        if (principal != null) {
            voteService.vote(postId, principal.getName(), VoteType.UPVOTE);
        }
        Post post = postRepository.findById(postId).orElseThrow();
        return ResponseEntity.ok().body(new VoteResponse(post.getVoteCount()));
    }


    @PostMapping("/{postId}/downvote")
    public ResponseEntity<?> downvote(@PathVariable Long postId, Principal principal) {
        if (principal != null) {
            voteService.vote(postId, principal.getName(), VoteType.DOWNVOTE);
        }
        Post post = postRepository.findById(postId).orElseThrow();
        return ResponseEntity.ok().body(new VoteResponse(post.getVoteCount()));
    }

    // Inner class for JSON response
    public static class VoteResponse {
        private int voteCount;
        public VoteResponse(int voteCount) { this.voteCount = voteCount; }
        public int getVoteCount() { return voteCount; }
    }
}
