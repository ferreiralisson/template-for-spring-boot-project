package com.example.demo.service.impl;

import com.example.demo.domain.Client;
import com.example.demo.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

import static com.example.demo.util.Constants.*;

@Slf4j
public class EmailServiceImpl implements EmailService {

    @Value("${default.sender}")
    private String sender;
    @Autowired
    private MailSender mailSender;

    protected SimpleMailMessage prepareNewPasswordEmail(Client client, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(client.getEmail());
        sm.setFrom(sender);
        sm.setSubject(SUBJECT_EMAIL);
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(MESSAGE_RECOVERY_PASSWORD_TITLE + newPass + MESSAGE_RECOVERY_PASSWORD_BODY);
        return sm;
    }

    @Override
    public void sendNewPasswordByEmail(Client client, String newPassword) {
        sendEmail(prepareNewPasswordEmail(client, newPassword));
    }

    @Override
    public void sendEmail(SimpleMailMessage msg) {
        log.info("Enviando email...");
        mailSender.send(msg);
        log.info("Email enviado");
    }

}
