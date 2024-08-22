package com.app.labdesoftware.services;

import com.app.labdesoftware.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "api.security.token.secret=mysecret")
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;
    @Value("${api.security.token.secret}")
    private String secret;

    //TODO: PEDIR UM HELP PRO TIO EDU
    @Test
    void generateToken() {
        User user = new User();
        user.setUsername("User");
        String token = tokenService.generateToken(user);
        Assertions.assertNotNull(token);
    }

    @Test
    void generateTokenException() {
        User user = new User();
        String token = tokenService.generateToken(user);
        Assertions.assertNotNull(token);
    }

    @Test
    void validateToken() {
        User user = new User();
        user.setUsername("User");
        String token = tokenService.generateToken(user);
        String subject = tokenService.validateToken(token);
        Assertions.assertEquals("User", subject);
    }

    @Test
    void validateTokenException() {
        String invalidToken = "invalid token";
        String subject = tokenService.validateToken(invalidToken);
        Assertions.assertEquals("", subject);
    }
}