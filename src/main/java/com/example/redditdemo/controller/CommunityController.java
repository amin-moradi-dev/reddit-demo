package com.example.redditdemo.controller;

import com.example.redditdemo.model.Community;
import com.example.redditdemo.model.User;
import com.example.redditdemo.repository.CommunityRepository;
import com.example.redditdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommunityController {

    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    // Display all communities page
    @GetMapping("/communities")
    public String listCommunities(Model model) {
        model.addAttribute("communities", communityRepository.findAll());
        return "communities"; // Thymeleaf template: communities.html
    }

    // Join a community
    @PostMapping("/communities/{id}/join")
    @Transactional
    public String joinCommunity(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Community community = communityRepository.findById(id).orElseThrow();

        if (!community.getMembers().contains(user)) {
            community.getMembers().add(user);
        }

        return "redirect:/communities";
    }

    // Make all communities available globally (for sidebar fragment)
    @ModelAttribute("allCommunities")
    public List<Community> populateAllCommunities() {
        return communityRepository.findAll();
    }
}
