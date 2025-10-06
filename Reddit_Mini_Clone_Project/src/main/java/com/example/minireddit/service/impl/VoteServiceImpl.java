package com.example.minireddit.service.impl;


import com.example.minireddit.model.*;
import com.example.minireddit.repository.*;
import com.example.minireddit.service.VoteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository votes;
    public VoteServiceImpl(VoteRepository votes){ this.votes=votes; }


    @Override @Transactional
    public int castVote(Long userId, Long postId, int value){
        if (value != 1 && value != -1) throw new IllegalArgumentException("Vote must be +1 or -1");
        Vote v = votes.findByUser_IdAndPost_Id(userId, postId)
                .map(existing -> { existing.setValue(value); return existing; })
                .orElseGet(() -> {
                    Vote nv = new Vote();
                    User u = new User(); u.setId(userId); nv.setUser(u);
                    Post p = new Post(); p.setId(postId); nv.setPost(p);
                    nv.setValue(value);
                    return nv;
                });
        votes.save(v);
        return votes.sumForPost(postId);
    }
}