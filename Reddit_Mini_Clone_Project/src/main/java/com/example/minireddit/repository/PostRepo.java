package com.example.minireddit.repository;

import com.example.minireddit.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByCommunity_IdOrderByCreatedAtDesc(Long communityId);
    List<Post> findByParent_IdOrderByCreatedAtAsc(Long parentId);
    List<Post> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String q);
}


