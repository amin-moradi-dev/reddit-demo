package com.example.minireddit.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity @Table(name = "users")
@Getter @Setter @NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true, length = 190)
    private String email;


    @Column(nullable = false)
    private String password;


    @Column(nullable = false, length = 100)
    private String displayName;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role = Role.USER;


    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}