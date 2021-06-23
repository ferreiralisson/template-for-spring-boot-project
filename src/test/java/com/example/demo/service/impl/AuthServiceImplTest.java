package com.example.demo.service.impl;

import com.example.demo.domain.Client;
import com.example.demo.dto.EmailDTO;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.ClientRepository;
import com.example.demo.util.ClientCreator;
import com.example.demo.util.EmailCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Tests for auth service")
class AuthServiceImplTest {

    @Autowired
    private AuthServiceImpl authServiceImpl;

    @MockBean
    private ClientRepository clientRepositoryMock;

    @BeforeEach
    void setUp(){
        when(clientRepositoryMock.findByEmail(anyString())).thenReturn(Optional.of(ClientCreator.createClientValid()));
        when(clientRepositoryMock.save(any(Client.class))).thenReturn(ClientCreator.createClientValid());
    }

    @Test
    @DisplayName("Send new password client when successful")
    void sendNewPasswordClient_WhenSuccessful(){
        assertThatCode(() -> authServiceImpl.sendNewPassword(EmailCreator.emailCreator()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Send new password client when not successful")
    void sendNewPasswordClient_WhenNotSuccessful(){
        when(clientRepositoryMock.findByEmail(anyString())).thenReturn(any(Optional.class));
        EmailDTO emailDTO = new EmailDTO();
        assertThrows(BadRequestException.class, () -> authServiceImpl.sendNewPassword(emailDTO));
    }

}