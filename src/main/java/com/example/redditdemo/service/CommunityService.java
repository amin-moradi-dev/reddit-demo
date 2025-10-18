package com.example.redditdemo.service;

import com.example.redditdemo.model.Community;
import com.example.redditdemo.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    @Autowired
    private CommunityRepository communityRepository;

    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    public Community save(Community community) {
        return communityRepository.save(community);
    }
}
