package com.example.minireddit.service.imp;

import com.example.minireddit.service.MailService;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Override
    public void sendActivationMail(String to, String token) {
        System.out.println("🟢 [MockMail] Activation email to " + to + " | token=" + token);
    }

    @Override
    public void sendPasswordResetMail(String to, String token) {
        System.out.println("🟢 [MockMail] Password reset email to " + to + " | token=" + token);
    }
}

