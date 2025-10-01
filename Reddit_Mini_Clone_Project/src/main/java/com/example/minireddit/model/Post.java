package com.example.minireddit.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity
@Getter @Setter @NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    private User author;


    @ManyToOne(optional = false)
    private Community community;


    @ManyToOne @JoinColumn(name = "parent_id")
    private Post parent; // null => top-level post


    @Column(length = 200)
    private String title; // only for top-level


    @Lob @Column(nullable = false)
    private String body;


    private String imageUrl;


    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}