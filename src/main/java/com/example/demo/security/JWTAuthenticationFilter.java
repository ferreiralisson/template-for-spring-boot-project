package com.example.demo.security;

import com.example.demo.domain.UserSS;
import com.example.demo.dto.CredentialsDTO;
import com.example.demo.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Log4j2
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res){
        try {

            CredentialsDTO credentialsDTO = new ObjectMapper().readValue(req.getInputStream(), CredentialsDTO.class);
            UsernamePasswordAuthenticationToken authToken =  new UsernamePasswordAuthenticationToken(credentialsDTO.getEmail(),
                    credentialsDTO.getPassword(), Arrays.asList());
            return  authenticationManager.authenticate(authToken);

        } catch (IOException e) {
            log.error("Erro de IO em tempo de execução conforme mensagem: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        res.addHeader("Authorization", "Bearer " + jwtUtil.generateToken(((UserSS) auth.getPrincipal()).getUsername()));
        res.addHeader("access-control-expose-headers", "Authorization");
    }
}
