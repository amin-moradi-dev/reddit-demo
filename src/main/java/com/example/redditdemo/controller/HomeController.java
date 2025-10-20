package com.example.redditdemo.controller;

import com.example.redditdemo.model.VoteType;
import com.example.redditdemo.dto.PostDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private VoteRepository voteRepository;

    // 🏠 Homepage
    @GetMapping({"/", "/home"})
    public String home(@AuthenticationPrincipal UserDetails user, Model model) {

        // Get current username or fallback to "guest"
        String username = (user != null) ? user.getUsername() : "guest";
        model.addAttribute("username", username);

        // Fetch posts ordered by creation time descending
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("posts", posts);

        // Calculate total votes per post
        Map<Long, Integer> voteCounts = new HashMap<>();
        for (Post post : posts) {
            List<Vote> votes = voteRepository.findByPost(post);
            int totalVotes = votes.stream()
                    .mapToInt(v -> v.getVoteType() == VoteType.UPVOTE ? 1 : -1)
                    .sum();
            voteCounts.put(post.getId(), totalVotes);
        }
        model.addAttribute("voteCounts", voteCounts);


        // ---------------------------
        // NEW: Calculate comment counts
        // ---------------------------
        Map<Long, Integer> commentCounts = posts.stream()
                .collect(Collectors.toMap(
                        Post::getId,
                        post -> post.getComments() != null ? post.getComments().size() : 0
                ));
        model.addAttribute("commentCounts", commentCounts);
        // This allows Thymeleaf to safely use ${commentCounts[post.id]} without null errors

        // Add empty Post object for "new post" form
        model.addAttribute("newPost", new Post());
// ---------------------------
        // NEW: Add empty PostDTO for Thymeleaf form binding
        // ---------------------------
        model.addAttribute("newPostDto", new PostDTO()); // ✅ Added this line
        return "index"; // Render index.html
    }

    // 🔍 AJAX Search Endpoint
    @GetMapping("/api/search")
    @ResponseBody
    public List<PostDTO> searchPosts(@RequestParam String query) {

        // Search posts by title (case-insensitive)
        List<Post> posts = postRepository.findByTitleContainingIgnoreCase(query);

        // Map Posts to PostDTOs to avoid circular references
        return posts.stream()
                .map(post -> new PostDTO(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor() != null ? post.getAuthor().getUsername() : "Unknown",
                        post.getCommunity() != null ? post.getCommunity().getName() : "General"
                ))
                .collect(Collectors.toList());
    }
}
