package com.example.redditdemo.repository;

import com.example.redditdemo.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {
}
