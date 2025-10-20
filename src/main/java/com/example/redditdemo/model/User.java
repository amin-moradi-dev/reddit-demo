package com.example.redditdemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "`app_user`") // Use backticks to escape reserved keyword
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain letters and numbers")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    // Profile image with default value
    private String profileImageUrl = "/images/p-1.jpg";

    // Bidirectional Many-to-Many with Community
    @ManyToMany(mappedBy = "members")
    private Set<Community> joinedCommunities = new HashSet<>();

    // ===== Constructors =====
    public User() {
    }

    public User(String username, String password, String email, String profileImageUrl) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "/images/p-1.jpg";
    }

    // ===== Helper methods =====
    public void joinCommunity(Community community) {
        this.joinedCommunities.add(community);
        community.getMembers().add(this);
    }

    public void leaveCommunity(Community community) {
        this.joinedCommunities.remove(community);
        community.getMembers().remove(this);
    }
}
