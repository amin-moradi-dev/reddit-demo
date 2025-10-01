package com.example.minireddit.model;


import com.example.minireddit.model.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity
@Getter @Setter @NoArgsConstructor
public class Community {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true, length = 120)
    private String name;


    @Lob
    private String description;


    @ManyToOne(optional = false)
    private User owner;


    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}