package com.example.minireddit.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface VoteRepository extends JpaRepository<com.example.minireddit.vote.Vote, Long> {
    Optional<com.example.minireddit.vote.Vote> findByUser_IdAndPost_Id(Long userId, Long postId);


    @Query("select coalesce(sum(v.value),0) from Vote v where v.post.id = :postId")
    int sumForPost(@Param("postId") Long postId);
}