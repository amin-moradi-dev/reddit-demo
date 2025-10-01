package com.example.minireddit.service.impl;


import com.example.minireddit.dto.PostCreateDto;
import com.example.minireddit.model.*;
import com.example.minireddit.repository.*;
import com.example.minireddit.service.PostService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
public class PostServiceImpl implements PostService {
    private final PostRepository posts;
    private final CommunityRepository communities;
    private final UserRepository users;
    private final VoteRepository votes;
    public PostServiceImpl(PostRepository posts, CommunityRepository communities, UserRepository users, VoteRepository votes){
        this.posts=posts; this.communities=communities; this.users=users; this.votes=votes;
    }


    @Override public Page<Post> home(int page, int size){
        return posts.findTopLevel(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }


    @Override public Page<Post> community(Long cid, int page, int size){
        return posts.findTopLevelByCommunity(cid, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }


    @Override public Post create(Long authorId, PostCreateDto dto){
        Post p = new Post();
        p.setAuthor(users.findById(authorId).orElseThrow());
        p.setCommunity(communities.findById(dto.communityId()).orElseThrow());
        if (dto.parentId()!=null) {
            p.setParent(posts.findById(dto.parentId()).orElseThrow());
            p.setTitle(null);
        } else {
            p.setTitle(dto.title());
        }
        p.setBody(dto.body());
        p.setImageUrl(dto.imageUrl());
        return posts.save(p);
    }


    @Override public Post get(Long id){ return posts.findById(id).orElseThrow(); }


    @Override public int score(Long postId){ return votes.sumForPost(postId); }
}