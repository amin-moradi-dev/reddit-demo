package com.example.minireddit.service;

import com.example.minireddit.model.Community;
import java.util.List;

public interface CommunityService {

    /**
     * Returns all existing communities.
     */
    List<Community> listAll();

    /**
     * Gets a specific community by ID, or null if not found.
     */
    Community getById(Long id);

    /**
     * Saves a new or updated community.
     */
    Community save(Community community);

    /**
     * Deletes a community by ID.
     */
    void delete(Long id);
}
