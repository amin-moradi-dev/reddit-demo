package com.example.minireddit.model;

import jakarta.persistence.*;

@Entity
@Table(name = "votes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean upvote;  // true = upvote, false = downvote

    // ✅ Relationships
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // 🧩 Constructors
    public Vote() {}

    public Vote(User user, Post post, boolean upvote) {
        this.user = user;
        this.post = post;
        this.upvote = upvote;
    }

    // 🧠 Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isUpvote() { return upvote; }
    public void setUpvote(boolean upvote) { this.upvote = upvote; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }
}
