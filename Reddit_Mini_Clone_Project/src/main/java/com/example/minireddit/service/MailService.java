package com.example.minireddit.service;

public interface MailService {
    void sendActivationMail(String to, String token);
    void sendPasswordResetMail(String to, String token);
}

