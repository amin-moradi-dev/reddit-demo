package com.example.minireddit.service.imp;

import com.example.minireddit.model.Community;
import com.example.minireddit.model.Post;
import com.example.minireddit.repository.PostRepository;
import com.example.minireddit.service.PostService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repo;

    public PostServiceImpl(PostRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Post> listByCommunity(Community community) {
        return repo.findByCommunityOrderByCreationTimeDesc(community);
    }

    @Override
    public Post getById(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Post create(Post post) {
        return repo.save(post);
    }
}

