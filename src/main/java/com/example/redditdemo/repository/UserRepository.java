package com.example.redditdemo.repository;

import com.example.redditdemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ Return Optional<User> so you can use .orElseThrow() safely
    Optional<User> findByUsername(String username);

    // ✅ Check if username/email already exist
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // ✅ Find by email
    Optional<User> findByEmail(String email);
}
