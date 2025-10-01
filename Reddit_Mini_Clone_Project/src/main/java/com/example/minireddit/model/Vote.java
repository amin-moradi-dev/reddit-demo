package com.example.minireddit.vote;


import com.example.minireddit.model.Post;
import com.example.minireddit.model.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;


@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
@Getter @Setter @NoArgsConstructor
public class Vote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(optional = false)
    private User user;


    @ManyToOne(optional = false)
    private Post post;


    @Column(nullable = false)
    private Integer value; // -1 or +1


    @Column(nullable = false)
    private Instant createdAt = Instant.now();
}