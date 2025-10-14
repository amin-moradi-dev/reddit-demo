package com.example.minireddit.controller;

import com.example.minireddit.model.*;
import com.example.minireddit.repository.UserRepository;
import com.example.minireddit.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {

    private final CommunityService communityService;
    private final PostService postService;
    private final FileStorageService fileStorageService;
    private final UserRepository userRepo;
    private final VoteService voteService;

    public PostController(CommunityService communityService,
                          PostService postService,
                          FileStorageService fileStorageService,
                          UserRepository userRepo,
                          VoteService voteService) {
        this.communityService = communityService;
        this.postService = postService;
        this.fileStorageService = fileStorageService;
        this.userRepo = userRepo;
        this.voteService = voteService;
    }

    // 📝 Show new post form
    @GetMapping("/community/{id}/newpost")
    public String newPostForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Community community = communityService.getById(id);
        if (community == null) {
            redirectAttributes.addFlashAttribute("error", "Community not found!");
            return "redirect:/";
        }
        model.addAttribute("community", community);
        return "newpost";
    }

    // 🧩 Create new post
    @PostMapping("/community/{id}/newpost")
    public String createPost(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String contentHtml,
                             @RequestParam(required = false) MultipartFile file,
                             @AuthenticationPrincipal UserDetails userDetails,
                             RedirectAttributes redirectAttributes) {
        Community community = communityService.getById(id);
        if (community == null) {
            redirectAttributes.addFlashAttribute("error", "Community not found!");
            return "redirect:/";
        }

        if (userDetails == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to post!");
            return "redirect:/login";
        }

        try {
            // ✅ find current user
            User author = userRepo.findByEmail(userDetails.getUsername().toLowerCase())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // ✅ create post
            Post post = new Post();
            post.setCommunity(community);
            post.setTitle(title);
            post.setContentHtml(contentHtml);
            post.setAuthor(author);

            // ✅ handle file upload if provided
            if (file != null && !file.isEmpty()) {
                String storedPath = fileStorageService.store(file);
                post.setAttachmentPath(storedPath);
            }

            postService.create(post);
            redirectAttributes.addFlashAttribute("success", "Post created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create post: " + e.getMessage());
        }

        return "redirect:/community/" + id;
    }

    // 👁️ View post page
    @GetMapping("/post/{id}")
    public String viewPost(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Post post = postService.getById(id);
        if (post == null) {
            redirectAttributes.addFlashAttribute("error", "Post not found!");
            return "redirect:/";
        }

        long upvotes = voteService.countUpvotes(post);
        long downvotes = voteService.countDownvotes(post);

        model.addAttribute("post", post);
        model.addAttribute("upvotes", upvotes);
        model.addAttribute("downvotes", downvotes);

        return "post";
    }
}
