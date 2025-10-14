package com.example.minireddit.controller;

import com.example.minireddit.service.CommunityService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CommunityService communityService;

    public HomeController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("communities", communityService.findAllCommunities());
        return "index";
    }
}
