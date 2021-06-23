package com.example.demo.controller;

import com.example.demo.dto.EmailDTO;
import com.example.demo.domain.UserSS;
import com.example.demo.service.AuthService;
import com.example.demo.service.ClientService;
import com.example.demo.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.example.demo.util.Constants.API_URL_PREFIX;
import static com.example.demo.util.Util.localDateTimeToString;
import static java.time.LocalDateTime.now;

@RestController
@RequestMapping(path = API_URL_PREFIX + "/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JWTUtil jwtUtil;
    private final AuthService authService;
    private final ClientService clientService;

    @PostMapping(path = "/refresh-token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = clientService.authenticated();
        log.info("Refresh token do client, start -  {}", localDateTimeToString(now()));
        if(user != null) {
            response.addHeader("Authorization", "Bearer " + jwtUtil.generateToken(user.getUsername()));
            response.addHeader("access-control-expose-headers", "Authorization");
        }
        log.info("Refresh token do client, finish -  {}", localDateTimeToString(now()));
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/forgot")
    public ResponseEntity<Void> recorevyPassword(@RequestBody @Valid EmailDTO emailDTO){
        log.info("Recuperação de senha em authService.sendNewPassword, start -  {}",
                localDateTimeToString(now()));
        authService.sendNewPassword(emailDTO);
        log.info("Recuperação de senha em authService.sendNewPassword, finish -  {}",
                localDateTimeToString(now()));
        return ResponseEntity.noContent().build();
    }
}
