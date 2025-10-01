package com.example.minireddit.service;


public interface VoteService {
    int castVote(Long userId, Long postId, int value); // returns new score
}