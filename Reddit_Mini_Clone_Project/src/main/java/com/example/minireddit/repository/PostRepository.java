package com.example.minireddit.repository;

import com.example.minireddit.model.Post;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.parent is null order by p.createdAt desc")
    Page<Post> findTopLevel(Pageable pageable);


    @Query("select p from Post p where p.parent is null and p.community.id = :cid order by p.createdAt desc")
    Page<Post> findTopLevelByCommunity(@Param("cid") Long communityId, Pageable pageable);


    List<Post> findByParent_IdOrderByCreatedAtAsc(Long parentId);
}

