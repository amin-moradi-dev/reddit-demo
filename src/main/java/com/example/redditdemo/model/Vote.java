package com.example.redditdemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Use EnumType.STRING to store "UPVOTE" or "DOWNVOTE" in the database
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VoteType voteType;

    // 🔗 Each vote belongs to a specific post
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // 🔗 Each vote is cast by a specific user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
