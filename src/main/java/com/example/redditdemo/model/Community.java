package com.example.redditdemo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String logoUrl;

    // ===== Many-to-Many relationship with users =====
    @ManyToMany
    @JoinTable(
            name = "community_membership",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members = new HashSet<>();

    // ===== Constructors =====
    public Community() {}

    public Community(String name, String description, String logoUrl) {
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
    }

    // ===== Helper methods =====
    public void addMember(User user) {
        this.members.add(user);
        user.getJoinedCommunities().add(this);
    }

    public void removeMember(User user) {
        this.members.remove(user);
        user.getJoinedCommunities().remove(this);
    }
}
