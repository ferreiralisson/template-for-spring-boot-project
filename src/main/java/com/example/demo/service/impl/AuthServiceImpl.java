package com.example.demo.service.impl;

import com.example.demo.domain.Client;
import com.example.demo.dto.EmailDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.AuthService;
import com.example.demo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.demo.util.Util.newPasswordRandom;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ClientRepository clientRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Override
    public void sendNewPassword(EmailDTO emailDTO) {
        Client client = clientRepository.findByEmail(emailDTO.getEmail()).orElseThrow(() -> new BadRequestException("Email não encontrado"));
        String newPassword = newPasswordRandom();
        client.setPassword(bCryptPasswordEncoder.encode(newPassword));
        clientRepository.save(client);
        emailService.sendNewPasswordByEmail(client, newPassword);
    }

}