package com.example.minireddit.repository;

import com.example.minireddit.model.Comment;
import com.example.minireddit.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostOrderByCreationTimeAsc(Post post);
}
