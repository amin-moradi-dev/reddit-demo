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

    private int value; // 1 or -1

    @ManyToOne
    private Post post;

    @ManyToOne
    private User user;
}
