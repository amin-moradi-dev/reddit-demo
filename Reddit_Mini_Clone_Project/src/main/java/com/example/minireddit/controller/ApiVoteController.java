package com.example.minireddit.controller;

import com.example.minireddit.model.User;
import com.example.minireddit.repository.UserRepo;
import com.example.minireddit.service.VoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class ApiVoteController {
    private final VoteService voteService;
    private final UserRepo userRepo;
    public ApiVoteController(VoteService voteService, UserRepo userRepo){
        this.voteService = voteService; this.userRepo = userRepo;
    }

    @PostMapping("/{id}/upvote")
    public ResponseEntity<?> up(@AuthenticationPrincipal UserDetails principal, @PathVariable Long id){
        if (principal == null) return ResponseEntity.status(401).build();
        User me = userRepo.findByUsername(principal.getUsername()).orElseThrow();
        long score = voteService.vote(me, id, 1);
        return ResponseEntity.ok(new Score(score));
    }

    @PostMapping("/{id}/downvote")
    public ResponseEntity<?> down(@AuthenticationPrincipal UserDetails principal, @PathVariable Long id){
        if (principal == null) return ResponseEntity.status(401).build();
        User me = userRepo.findByUsername(principal.getUsername()).orElseThrow();
        long score = voteService.vote(me, id, -1);
        return ResponseEntity.ok(new Score(score));
    }

    record Score(long score){}
}
