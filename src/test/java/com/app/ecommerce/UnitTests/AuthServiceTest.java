package com.app.ecommerce.UnitTests;

import com.app.ecommerce.entities.User;
import com.app.ecommerce.enumerations.UserRole;
import com.app.ecommerce.repositories.UserRepository;
import com.app.ecommerce.services.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Should validate if the username exists to authenticate the user")
    void loadUserByUsername() {
        User user = new User("User", "User", UserRole.USER);
        when(userRepository.findByUsername("User")).thenReturn(user);
        UserDetails userDetails = authService.loadUserByUsername("User");
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals("User",userDetails.getUsername());
    }

}