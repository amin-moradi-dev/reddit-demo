package com.example.minireddit.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="votes")
public class Vote {
    @EmbeddedId
    private VoteId id;

    @MapsId("postId")
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="post_id", nullable=false)
    private Post post;

    @MapsId("userId")
    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name="vote_type", nullable=false)
    private int voteType; // -1 or +1

    @Column(name="created_at", nullable=false)
    private Instant createdAt = Instant.now();

    public Vote(){}

    @PrePersist @PreUpdate
    private void validate(){
        if (voteType != -1 && voteType != 1) throw new IllegalArgumentException("voteType must be -1 or +1");
    }

    // getters/setters
    public VoteId getId(){return id;}
    public void setId(VoteId id){this.id=id;}
    public Post getPost(){return post;}
    public void setPost(Post post){this.post=post;}
    public User getUser(){return user;}
    public void setUser(User user){this.user=user;}
    public int getVoteType(){return voteType;}
    public void setVoteType(int voteType){this.voteType=voteType;}
    public Instant getCreatedAt(){return createdAt;}
    public void setCreatedAt(Instant createdAt){this.createdAt=createdAt;}
}
