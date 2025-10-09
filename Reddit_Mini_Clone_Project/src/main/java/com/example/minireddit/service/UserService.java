package com.example.minireddit.service;

import com.example.minireddit.model.User;

public interface UserService {
    User register(String fullName, String email, String password);
    void enableUser(Long userId);
}

