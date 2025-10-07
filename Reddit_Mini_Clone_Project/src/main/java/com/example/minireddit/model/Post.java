package com.example.minireddit.model;


import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="posts")
public class Post {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="community_id")
    private Community community; // only for top-level

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Post parent; // null for top-level; non-null for comment

    @Column(length=200)
    private String title; // only top-level

    @Lob @Column(nullable=false)
    private String body;

    @Column(name="image_url", length=255)
    private String imageUrl;

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt = Instant.now();

    public Post(){}

    @PrePersist @PreUpdate
    private void validateKind(){
        boolean isTop = (parent == null);
        if (isTop) {
            if (community == null) throw new IllegalStateException("Top-level posts require community");
            if (title == null || title.isBlank()) throw new IllegalStateException("Top-level posts require title");
        } else {
            if (community != null) throw new IllegalStateException("Comments must not have community");
            if (title != null && !title.isBlank()) throw new IllegalStateException("Comments must not have title");
        }
    }

    // getters/setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}
    public Community getCommunity(){return community;}
    public void setCommunity(Community community){this.community=community;}
    public Post getParent(){return parent;}
    public void setParent(Post parent){this.parent=parent;}
    public String getTitle(){return title;}
    public void setTitle(String title){this.title=title;}
    public String getBody(){return body;}
    public void setBody(String body){this.body=body;}
    public String getImageUrl(){return imageUrl;}
    public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}
    public Instant getCreatedAt(){return createdAt;}
    public void setCreatedAt(Instant createdAt){this.createdAt=createdAt;}
}
