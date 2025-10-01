package com.example.minireddit.controller;

import com.example.minireddit.dto.CommunityCreateDto;
import com.example.minireddit.model.Community;
import com.example.minireddit.repository.CommunityRepository;
import com.example.minireddit.service.PostService;
import com.example.minireddit.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller @RequestMapping("/community")
public class CommunityController {
    private final CommunityRepository communities; private final PostService posts; private final UserService users;
    public CommunityController(CommunityRepository c, PostService p, UserService u){ this.communities=c; this.posts=p; this.users=u; }


    @GetMapping("/new")
    public String newForm(Model model){ model.addAttribute("dto", new CommunityCreateDto("","")); return "community/form"; }


    @PostMapping
    public String create(@ModelAttribute("dto") @Valid CommunityCreateDto dto){
        Community c = new Community();
        c.setName(dto.name()); c.setDescription(dto.description());
        c.setOwner(users.currentUser());
        communities.save(c);
        return "redirect:/community/"+c.getId();
    }


    @GetMapping("/{id}")
    public String view(@PathVariable Long id, @RequestParam(defaultValue="0") int page, Model model){
        Community c = communities.findById(id).orElseThrow();
        model.addAttribute("community", c);
        model.addAttribute("feed", posts.community(id, page, 10));
        return "community/view";
    }
}
