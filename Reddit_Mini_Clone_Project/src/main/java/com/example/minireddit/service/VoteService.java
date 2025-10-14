package com.example.minireddit.service;

import com.example.minireddit.model.Post;
import com.example.minireddit.model.User;

public interface VoteService {
    void vote(User user, Post post, int value);
    long countUpvotes(Post post);
    long countDownvotes(Post post);
}

