package com.example.minireddit.repository;

import com.example.minireddit.model.Vote;
import com.example.minireddit.model.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepo extends JpaRepository<Vote, VoteId> {
    long countByPost_IdAndVoteType(Long postId, int voteType);
}
