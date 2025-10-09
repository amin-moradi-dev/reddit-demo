package com.example.minireddit.service.imp;

import com.example.minireddit.model.Community;
import com.example.minireddit.repository.CommunityRepository;
import com.example.minireddit.service.CommunityService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepository communityRepo;

    public CommunityServiceImpl(CommunityRepository communityRepo) {
        this.communityRepo = communityRepo;
    }

    @Override
    public List<Community> listAll() {
        return communityRepo.findAll();
    }

    @Override
    public Community getById(Long id) {
        return communityRepo.findById(id).orElse(null);
    }

    @Override
    public Community save(Community community) {
        return communityRepo.save(community);
    }

    @Override
    public void delete(Long id) {
        communityRepo.deleteById(id);
    }
    public List<Community> findAllCommunities() {
        return communityRepo.findAll();
    }
}
