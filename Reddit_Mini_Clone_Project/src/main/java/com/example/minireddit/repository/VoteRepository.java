package com.example.minireddit.repository;

import com.example.minireddit.model.Post;
import com.example.minireddit.model.User;
import com.example.minireddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByUserAndPost(User user, Post post);

    // ✅ Count upvotes (upvote = true)
    long countByPostAndUpvoteTrue(Post post);

    // ✅ Count downvotes (upvote = false)
    long countByPostAndUpvoteFalse(Post post);
}
