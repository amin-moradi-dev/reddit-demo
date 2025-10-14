package com.example.minireddit.repository;

import com.example.minireddit.model.Post;
import com.example.minireddit.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCommunityOrderByCreationTimeDesc(Community community);
}
