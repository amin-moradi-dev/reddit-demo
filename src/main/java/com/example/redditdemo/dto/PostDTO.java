package com.example.redditdemo.dto;

import org.springframework.web.multipart.MultipartFile;

public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private String username;
    private String communityName;

    // ✅ New field for uploaded image
    private MultipartFile imageFile;

    // ===== Constructors =====
    public PostDTO() {
        // no-args constructor required for Spring form binding
    }

    public PostDTO(Long id, String title, String content, String username, String communityName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.communityName = communityName;
    }

    // ===== Getters and Setters =====
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getCommunityName() { return communityName; }
    public void setCommunityName(String communityName) { this.communityName = communityName; }

    public MultipartFile getImageFile() { return imageFile; }
    public void setImageFile(MultipartFile imageFile) { this.imageFile = imageFile; }
}
