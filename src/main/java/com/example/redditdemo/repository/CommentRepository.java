package com.example.redditdemo.repository;

import com.example.redditdemo.model.Comment;
import com.example.redditdemo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Fetch all comments for a specific post, ordered by creation time
    List<Comment> findByPostOrderByCreatedAtDesc(Post post);
}
