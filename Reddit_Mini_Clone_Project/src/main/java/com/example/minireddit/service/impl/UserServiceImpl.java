package com.example.minireddit.service.impl;


import com.example.minireddit.dto.RegisterDto;
import com.example.minireddit.model.*;
import com.example.minireddit.model.User;
import com.example.minireddit.repository.UserRepository;
import com.example.minireddit.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository users; private final PasswordEncoder encoder;
    public UserServiceImpl(UserRepository users, PasswordEncoder encoder){ this.users=users; this.encoder=encoder; }


    @Override
    public User register(RegisterDto dto){
        User u = new User();
        u.setEmail(dto.email());
        u.setPassword(encoder.encode(dto.password()));
        u.setDisplayName(dto.displayName());
        u.setRole(Role.USER);
        return users.save(u);
    }


    @Override
    public Long currentUserId(){ var u=currentUser(); return u==null? null : u.getId(); }


    @Override
    public User currentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth==null || !(auth.getPrincipal() instanceof UserDetails ud)) return null;
        return users.findByEmail(ud.getUsername()).orElse(null);
    }


    // --- Spring Security integration ---
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = users.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getPassword())
                .roles(u.getRole().name())
                .build();
    }
}