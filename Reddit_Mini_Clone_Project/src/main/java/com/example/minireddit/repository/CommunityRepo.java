package com.example.minireddit.repository;

import com.example.minireddit.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepo extends JpaRepository<Community, Long> {}
