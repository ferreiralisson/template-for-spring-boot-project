package com.example.demo.service;

import com.example.demo.dto.EmailDTO;

public interface AuthService {
    void sendNewPassword(EmailDTO emailDTO);
}
