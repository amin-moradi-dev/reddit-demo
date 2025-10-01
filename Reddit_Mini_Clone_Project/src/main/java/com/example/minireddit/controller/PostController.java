package com.example.minireddit.controller;


import com.example.minireddit.dto.PostCreateDto;
import com.example.minireddit.model.Post;
import com.example.minireddit.repository.CommunityRepository;
import com.example.minireddit.repository.PostRepository;
import com.example.minireddit.service.PostService;
import com.example.minireddit.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller @RequestMapping("/post")
public class PostController {
    private final PostService posts; private final PostRepository postRepo; private final CommunityRepository communities; private final UserService users;
    public PostController(PostService posts, PostRepository postRepo, CommunityRepository communities, UserService users){
        this.posts=posts; this.postRepo=postRepo; this.communities=communities; this.users=users; }


    @GetMapping("/new")
    public String newForm(@RequestParam(required=false) Long communityId, Model model){
        model.addAttribute("dto", new PostCreateDto(communityId, "", "", "", null));
        model.addAttribute("communities", communities.findAll());
        return "post/form";
    }


    @PostMapping
    public String create(@ModelAttribute("dto") @Valid PostCreateDto dto){
        Long uid = users.currentUserId();
        Post p = posts.create(uid, dto);
        return "redirect:/post/" + p.getId();
    }


    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model){
        Post p = posts.get(id);
        model.addAttribute("post", p);
        model.addAttribute("comments", postRepo.findByParent_IdOrderByCreatedAtAsc(id));
        model.addAttribute("score", posts.score(id));
        return "post/view";
    }


    @PostMapping("/{id}/comment")
    public String comment(@PathVariable Long id, @RequestParam String body){
        Long uid = users.currentUserId();
        PostCreateDto dto = new PostCreateDto(posts.get(id).getCommunity().getId(), null, body, null, id);
        posts.create(uid, dto);
        return "redirect:/post/" + id + "#comments";
    }
}