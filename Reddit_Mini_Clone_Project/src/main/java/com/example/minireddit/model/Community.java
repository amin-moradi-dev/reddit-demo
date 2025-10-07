package com.example.minireddit.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="communities")
public class Community {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=100)
    private String name;

    @Lob
    private String description;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;

    @Column(name="created_at", nullable=false, updatable=false)
    private Instant createdAt = Instant.now();

    public Community(){}

    // getters/setters
    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getDescription(){return description;}
    public void setDescription(String description){this.description=description;}
    public User getOwner(){return owner;}
    public void setOwner(User owner){this.owner=owner;}
    public Instant getCreatedAt(){return createdAt;}
    public void setCreatedAt(Instant createdAt){this.createdAt=createdAt;}
}
