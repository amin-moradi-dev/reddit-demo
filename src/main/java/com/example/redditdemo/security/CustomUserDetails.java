package com.example.redditdemo.security;

import com.example.redditdemo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Make your custom fields accessible
    public String getProfileImageUrl() {
        // ✅ Fallback: if user has no profile image, return default image path
        if (user.getProfileImageUrl() == null || user.getProfileImageUrl().isEmpty()) {
            return "/images/p-1.jpg";
        }
        return user.getProfileImageUrl();
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // No roles yet, we can add later
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
