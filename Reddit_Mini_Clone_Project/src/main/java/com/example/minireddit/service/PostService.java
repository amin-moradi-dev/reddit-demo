package com.example.minireddit.service;


import com.example.minireddit.dto.PostCreateDto;
import com.example.minireddit.model.Post;
import org.springframework.data.domain.Page;


public interface PostService {
    Page<Post> home(int page, int size);
    Page<Post> community(Long communityId, int page, int size);
    Post create(Long authorId, PostCreateDto dto);
    Post get(Long id);
    int score(Long postId);
}