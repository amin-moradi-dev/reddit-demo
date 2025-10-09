package com.example.minireddit.service;

import com.example.minireddit.model.Community;
import com.example.minireddit.model.Post;
import java.util.List;

public interface PostService {
    List<Post> listByCommunity(Community community);
    Post getById(Long id);
    Post create(Post post);
}

