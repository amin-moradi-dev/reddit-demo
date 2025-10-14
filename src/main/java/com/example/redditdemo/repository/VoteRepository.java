package com.example.redditdemo.repository;

import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByPost(Post post);
}
