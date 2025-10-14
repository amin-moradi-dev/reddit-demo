package com.example.minireddit.service.imp;

import com.example.minireddit.model.*;
import com.example.minireddit.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepo;
    private final CommunityRepository commRepo;
    private final PostRepository postRepo;
    private final PasswordEncoder encoder;

    public DataInitializer(UserRepository userRepo,
                           CommunityRepository commRepo,
                           PostRepository postRepo,
                           PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.commRepo = commRepo;
        this.postRepo = postRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (userRepo.count() == 0) {
            // ✅ Create Admin User
            User admin = new User();
            admin.setFullName("Admin User");
            admin.setEmail("admin@example.com");
            admin.setPasswordHash(encoder.encode("admin123"));
            admin.setEnabled(true);
            admin.setRoles(Set.of(Role.ADMIN, Role.USER));
            userRepo.save(admin);

            // ✅ Create Community (linked to User, not ownerId)
            Community community = new Community();
            community.setName("Spring Boot Fans");
            community.setDescription("A place to share Spring Boot experiences.");
            community.setOwner(admin);
            commRepo.save(community);

            // ✅ Create Post (linked to User and Community)
            Post post = new Post();
            post.setCommunity(community);
            post.setAuthor(admin);
            post.setTitle("Welcome to MiniReddit+");
            post.setContentHtml("<p>This is your first post. Enjoy exploring!</p>");
            postRepo.save(post);

            System.out.println("🟢 Demo data created: admin@example.com / admin123");
        }
    }
}
