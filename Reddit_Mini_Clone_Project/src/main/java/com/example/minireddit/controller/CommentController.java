package com.example.minireddit.controller;

import com.example.minireddit.model.Comment;
import com.example.minireddit.model.Post;
import com.example.minireddit.model.User;
import com.example.minireddit.repository.CommentRepository;
import com.example.minireddit.repository.PostRepository;
import com.example.minireddit.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentRepository commentRepo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;

    public CommentController(CommentRepository commentRepo,
                             PostRepository postRepo,
                             UserRepository userRepo) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    @PostMapping("/{postId}/reply")
    public String replyToPost(@PathVariable("postId") Long postId,
                              @RequestParam String body,
                              @AuthenticationPrincipal UserDetails userDetails,
                              RedirectAttributes redirectAttributes) {
        // ✅ 1. Find the post
        Post post = postRepo.findById(postId).orElse(null);
        if (post == null) {
            redirectAttributes.addFlashAttribute("error", "Post not found!");
            return "redirect:/";
        }

        // ✅ 2. Find the current user
        if (userDetails == null) {
            redirectAttributes.addFlashAttribute("error", "You must be logged in to comment!");
            return "redirect:/login";
        }
        User currentUser = userRepo.findByEmail(userDetails.getUsername().toLowerCase())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        // ✅ 3. Create and save the comment
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(currentUser);
        comment.setBody(body);

        commentRepo.save(comment);

        redirectAttributes.addFlashAttribute("success", "Comment added successfully!");
        return "redirect:/post/" + postId;
    }
}
