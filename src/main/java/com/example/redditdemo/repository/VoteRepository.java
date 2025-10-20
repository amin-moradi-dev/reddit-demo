package com.example.redditdemo.repository;

import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.User;
import com.example.redditdemo.model.Vote;
import com.example.redditdemo.model.VoteType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    // existing method
    List<Vote> findByPost(Post post);

    // find a user's vote on a specific post
    Vote findByPostAndUser(Post post, User user);

    // count votes by type for a post
    int countByPostAndVoteType(Post post, VoteType voteType);
}
