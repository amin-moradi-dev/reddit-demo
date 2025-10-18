package com.example.redditdemo.controller;

import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.Vote;
import com.example.redditdemo.repository.PostRepository;
import com.example.redditdemo.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails user, Model model) {

        String username = (user != null) ? user.getUsername() : "guest";
        model.addAttribute("username", username);

        List<Post> posts = postRepository.findAllByOrderByCreationTimeDesc();
        model.addAttribute("posts", posts);

        Map<Long, Integer> voteCounts = new HashMap<>();
        for (Post post : posts) {
            List<Vote> votes = voteRepository.findByPost(post);
            int totalVotes = votes.stream().mapToInt(Vote::getValue).sum();
            voteCounts.put(post.getId(), totalVotes);
        }
        model.addAttribute("voteCounts", voteCounts);

        model.addAttribute("newPost", new Post());

        return "index";
    }
}
