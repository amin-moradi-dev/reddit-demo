package com.example.minireddit.controller;

import com.example.minireddit.model.Community;
import com.example.minireddit.service.CommunityService;
import com.example.minireddit.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;
    private final PostService postService;

    public CommunityController(CommunityService communityService, PostService postService) {
        this.communityService = communityService;
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public String viewCommunity(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Community c = communityService.getById(id);
        if (c == null) {
            redirectAttributes.addFlashAttribute("error", "Community not found!");
            return "redirect:/";
        }
        model.addAttribute("community", c);
        model.addAttribute("posts", postService.listByCommunity(c));
        return "community";
    }
}
