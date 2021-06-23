package com.example.demo.service;

import com.example.demo.domain.Client;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
    void sendEmail(SimpleMailMessage msg);
    void sendNewPasswordByEmail(Client client, String newPassword);
}
