package com.example.redditdemo.repository;

import com.example.redditdemo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // Example in your PostRepository
    List<Post> findAllByOrderByCreatedAtDesc(); // newest posts first

    // Search posts by title (case-insensitive)
    List<Post> findByTitleContainingIgnoreCase(String query);
}
