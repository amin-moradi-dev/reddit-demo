package com.example.minireddit.service;


import com.example.minireddit.dto.RegisterDto;
import com.example.minireddit.model.User;


public interface UserService {
    User register(RegisterDto dto);
    Long currentUserId();
    User currentUser();
}