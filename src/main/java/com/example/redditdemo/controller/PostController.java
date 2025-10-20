package com.example.redditdemo.controller;

import com.example.redditdemo.model.Post;
import com.example.redditdemo.model.User;
import com.example.redditdemo.model.Comment;
import com.example.redditdemo.repository.PostRepository;
import com.example.redditdemo.repository.UserRepository;
import com.example.redditdemo.repository.CommentRepository;
import com.example.redditdemo.dto.PostDTO; // ✅ UPDATED: import PostDTO for form binding
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile; // ✅ UPDATED: for file upload

import java.io.IOException; // ✅ UPDATED
import java.nio.file.Files; // ✅ UPDATED
import java.nio.file.Path; // ✅ UPDATED
import java.nio.file.Paths; // ✅ UPDATED
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    // ----------------------------
    // Create a new post (with image upload)
    // ----------------------------



    @PostMapping("/create")
    public String createPost(@ModelAttribute PostDTO newPostDto,
                             @AuthenticationPrincipal UserDetails currentUser) throws IOException {

        User author = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(newPostDto.getTitle());
        post.setContent(newPostDto.getContent());
        post.setAuthor(author);

        MultipartFile file = newPostDto.getImageFile();
        if (file != null && !file.isEmpty()) {

            // ✅ Absolute path inside project resources
            String uploadDir = "src/main/resources/static/images/posts/";
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath();

            // ✅ Create folder if it doesn't exist
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // ✅ Unique filename
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            // ✅ Save the file
            file.transferTo(filePath.toFile());

            // ✅ Set URL relative to static folder
            post.setImageUrl("/images/posts/" + fileName);
        }

        postRepository.save(post);

        return "redirect:/";
    }







    // ----------------------------
    // Display a single post with comments
    // ----------------------------
    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id,
                          Model model,
                          @AuthenticationPrincipal UserDetails currentUser) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        List<Comment> comments = commentRepository.findByPostOrderByCreatedAtDesc(post);

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);

        String username = (currentUser != null) ? currentUser.getUsername() : "guest";
        model.addAttribute("username", username);

        // Fresh comment object for form
        model.addAttribute("newComment", new Comment());

        return "post-details";
    }

    // ----------------------------
    // Normal form submission for comments
    // ----------------------------
    @PostMapping("/{id}/comments")
    public String addComment(@PathVariable Long id,
                             @ModelAttribute("newComment") Comment newComment,
                             @AuthenticationPrincipal UserDetails currentUser) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User author = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setContent(newComment.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        return "redirect:/posts/" + id;
    }

    // ----------------------------
    // AJAX submission endpoint for comments
    // ----------------------------
    @PostMapping("/{id}/comments/ajax")
    @ResponseBody
    public Map<String, String> addCommentAjax(@PathVariable Long id,
                                              @RequestBody Comment newComment,
                                              @AuthenticationPrincipal UserDetails currentUser) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        User author = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setAuthor(author);
        comment.setContent(newComment.getContent());
        comment.setCreatedAt(LocalDateTime.now());

        commentRepository.save(comment);

        // ✅ Return profile image URL for AJAX rendering
        return Map.of(
                "author", author.getUsername(),
                "content", comment.getContent(),
                "createdAt", comment.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                "profileImageUrl", author.getProfileImageUrl()
        );
    }

}
