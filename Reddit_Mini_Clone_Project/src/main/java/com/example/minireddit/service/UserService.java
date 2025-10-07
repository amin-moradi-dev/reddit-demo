package com.example.minireddit.service;

import com.example.minireddit.model.Role;
import com.example.minireddit.model.User;
import com.example.minireddit.repository.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepo repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepo repo, PasswordEncoder encoder){
        this.repo = repo; this.encoder = encoder;
    }

    @Transactional
    public User register(String email, String username, String fullName, String rawPassword){
        if (repo.findByUsername(username).isPresent()) throw new IllegalArgumentException("Username exists");
        if (repo.findByEmail(email).isPresent()) throw new IllegalArgumentException("Email exists");
        User u = new User();
        u.setEmail(email);
        u.setUsername(username);
        u.setFullName(fullName);
        u.setPassword(encoder.encode(rawPassword));
        u.setRole(Role.USER);
        return repo.save(u);
    }
}

