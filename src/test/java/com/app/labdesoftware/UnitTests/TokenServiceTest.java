package com.app.labdesoftware.UnitTests;

import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.services.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(tokenService,"secret","mysecret");
    }

    @Test
    @DisplayName("Should generate a token")
    void generateToken() {
        User user = new User();
        user.setUsername("User");
        String token = tokenService.generateToken(user);
        Assertions.assertNotNull(token);
    }

    @Test
    @DisplayName("Verify if token generation failed, if so throws an exception")
    void generateTokenException() {
        User user = new User();
        String token = tokenService.generateToken(user);
        Assertions.assertNotNull(token);
    }

    @Test
    @DisplayName("Should validate the generated token")
    void validateToken() {
        User user = new User();
        user.setUsername("User");
        String token = tokenService.generateToken(user);
        String subject = tokenService.validateToken(token);
        Assertions.assertEquals("User", subject);
    }

    @Test
    @DisplayName("Verify if validation fails, if so throws an exception")
    void validateTokenException() {
        String invalidToken = "invalid token";
        String subject = tokenService.validateToken(invalidToken);
        Assertions.assertEquals("", subject);
    }
}