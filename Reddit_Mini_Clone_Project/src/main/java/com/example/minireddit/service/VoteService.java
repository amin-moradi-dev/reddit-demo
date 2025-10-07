package com.example.minireddit.service;

import com.example.minireddit.model.*;
import com.example.minireddit.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoteService {
    private final VoteRepo voteRepo;
    private final PostRepo postRepo;

    public VoteService(VoteRepo voteRepo, PostRepo postRepo){
        this.voteRepo = voteRepo; this.postRepo = postRepo;
    }

    @Transactional
    public long vote(User voter, Long postId, int value){
        if (value != -1 && value != 1) throw new IllegalArgumentException("value must be -1 or +1");
        Post post = postRepo.findById(postId).orElseThrow();
        VoteId id = new VoteId(postId, voter.getId());
        Vote v = voteRepo.findById(id).orElseGet(() -> {
            Vote nv = new Vote();
            nv.setId(id); nv.setPost(post); nv.setUser(voter);
            return nv;
        });
        v.setVoteType(value);
        voteRepo.save(v);
        long ups = voteRepo.countByPost_IdAndVoteType(postId, 1);
        long downs = voteRepo.countByPost_IdAndVoteType(postId, -1);
        return ups - downs;
    }
}
