package com.example.redditdemo.controller;

import com.example.redditdemo.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    @GetMapping("/communities")
    public String listCommunities(Model model) {
        model.addAttribute("communities", communityService.getAllCommunities());
        return "communities"; // Thymeleaf template
    }
}
