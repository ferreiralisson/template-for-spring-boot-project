package com.example.demo.controller;

import com.example.demo.dto.EmailDTO;
import com.example.demo.service.AuthService;
import com.example.demo.util.EmailCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for auth controller")
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    void setup(){
        doNothing().when(authService).sendNewPassword(any(EmailDTO.class));
    }

    @Test
    @DisplayName("Recovery password client when successful")
    void recoveryPasswordClient_WhenSuccessful(){
        assertThatCode(() -> authController.recorevyPassword(EmailCreator.emailCreator()))
                .doesNotThrowAnyException();
        ResponseEntity<Void> entity = authController.recorevyPassword(EmailCreator.emailCreator());
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(NO_CONTENT);
    }

}