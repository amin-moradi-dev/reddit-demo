package com.example.minireddit.service;

import com.example.minireddit.model.*;
import com.example.minireddit.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
    private final PostRepo postRepo;
    private final CommunityRepo communityRepo;

    public PostService(PostRepo postRepo, CommunityRepo communityRepo){
        this.postRepo = postRepo; this.communityRepo = communityRepo;
    }

    @Transactional
    public Post createTopLevelPost(User author, Long communityId, String title, String body, String imageUrl){
        Community c = communityRepo.findById(communityId).orElseThrow();
        Post p = new Post();
        p.setUser(author);
        p.setCommunity(c);
        p.setTitle(title);
        p.setBody(body);
        p.setImageUrl(imageUrl);
        return postRepo.save(p);
    }

    @Transactional
    public Post createComment(User author, Long parentId, String body){
        Post parent = postRepo.findById(parentId).orElseThrow();
        Post c = new Post();
        c.setUser(author);
        c.setParent(parent);
        c.setBody(body);
        return postRepo.save(c);
    }
}
