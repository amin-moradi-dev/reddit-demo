package com.example.minireddit.config;

import com.example.minireddit.model.*;
import com.example.minireddit.repository.CommunityRepository;
import com.example.minireddit.repository.PostRepository;
import com.example.minireddit.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CommunityRepository communityRepo;
    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(CommunityRepository communityRepo,
                      UserRepository userRepo,
                      PostRepository postRepo,
                      PasswordEncoder passwordEncoder) {
        this.communityRepo = communityRepo;
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // ✅ 1. Seed default admin user
        User admin = userRepo.findByEmail("admin@demo.com").orElse(null);
        if (admin == null) {
            admin = new User();
            admin.setFullName("Demo Admin");
            admin.setEmail("admin@demo.com");
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("123")); // use encoder!
            admin.setEnabled(true);
            admin.setRoles(Set.of(Role.ADMIN, Role.USER));
            userRepo.save(admin);
        }

        // ✅ 2. Seed default community
        Community community = communityRepo.findByName("General Discussion").orElse(null);
        if (community == null) {
            community = new Community();
            community.setName("General Discussion");
            community.setDescription("A default community for general topics and announcements.");
            community.setOwner(admin);
            community.setCreationTime(LocalDateTime.now());
            communityRepo.save(community);
        }

        // ✅ 3. Seed default post
        if (postRepo.count() == 0) {
            Post post = new Post();
            post.setCommunity(community);
            post.setAuthor(admin);
            post.setTitle("Welcome to MiniReddit+!");
            post.setContentHtml("<p>This is the default post created automatically when the app starts.</p>");
            post.setCreationTime(LocalDateTime.now());
            postRepo.save(post);
        }

        System.out.println("✅ Default data seeded (user, community, post).");
    }
}
