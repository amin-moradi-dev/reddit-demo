package com.example.minireddit.service;

import com.example.minireddit.model.Role;
import com.example.minireddit.model.User;
import com.example.minireddit.repository.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepo repo;
    public UserDetailsServiceImpl(UserRepo repo){this.repo=repo;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found"));
        String role = "ROLE_" + (u.getRole() == Role.ADMIN ? "ADMIN":"USER");
        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(role)))
                .accountLocked(false).accountExpired(false).credentialsExpired(false).disabled(false)
                .build();
    }
}
