package com.example.minireddit.service.imp;

import com.example.minireddit.model.*;
import com.example.minireddit.repository.*;
import com.example.minireddit.service.MailService;
import com.example.minireddit.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;


import java.util.Set;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepo;
    private final VerificationTokenRepository tokenRepo;
    private final PasswordEncoder encoder;
    private final MailService mail;

    public UserServiceImpl(UserRepository userRepo,
                           VerificationTokenRepository tokenRepo,
                           PasswordEncoder encoder,
                           MailService mail) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.encoder = encoder;
        this.mail = mail;
    }

    @Override
    @Transactional
    public User register(String fullName, String email, String password) {
        if (userRepo.existsByEmail(email.toLowerCase()))
            throw new IllegalArgumentException("Email already registered");

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email.toLowerCase());
        user.setPasswordHash(encoder.encode(password));
        user.setRoles(Set.of(Role.USER));
        user.setEnabled(false);
        userRepo.save(user);

        // ✅ Create verification token correctly
        VerificationToken token = new VerificationToken(user);
        tokenRepo.save(token);

        // ✅ Send activation email
        mail.sendActivationMail(user.getEmail(), token.getId());

        return user;
    }

    @Override
    @Transactional
    public void enableUser(Long userId) {
        userRepo.findById(userId).ifPresent(user -> {
            user.setEnabled(true);
            userRepo.save(user);
        });
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepo.findByEmail(email.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // ✅ Use Spring Security’s User class, not your entity
        return org.springframework.security.core.userdetails.User.builder()
                .username(u.getEmail())
                .password(u.getPasswordHash())
                .roles(u.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .disabled(!u.isEnabled())
                .build();
    }
}
