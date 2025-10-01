package com.example.minireddit.controller;

import com.example.minireddit.service.UserService;
import com.example.minireddit.service.VoteService;
import org.springframework.web.bind.annotation.*;


@RestController @RequestMapping("/api/votes")
public class VoteApi {
    private final VoteService votes; private final UserService users;
    public VoteApi(VoteService v, UserService u){ this.votes=v; this.users=u; }


    public record VoteRequest(Long postId, Integer value) {}
    public record VoteResponse(Long postId, Integer score) {}


    @PostMapping
    public VoteResponse vote(@RequestBody VoteRequest req){
        Long uid = users.currentUserId();
        int score = votes.castVote(uid, req.postId(), req.value());
        return new VoteResponse(req.postId(), score);
    }
}