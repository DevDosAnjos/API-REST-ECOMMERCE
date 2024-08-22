package com.app.labdesoftware.services;

import com.app.labdesoftware.controllers.authentication.LoginRequest;
import com.app.labdesoftware.controllers.authentication.LoginResponse;
import com.app.labdesoftware.controllers.user.RegisterRequest;
import com.app.labdesoftware.controllers.user.RegisterResponse;
import com.app.labdesoftware.controllers.user.UserResponse;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumerations.UserRole;
import com.app.labdesoftware.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TokenService tokenService;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should get a list of all users")
    void ListAll() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        User user2 = new User("Admin2", "Admin2", UserRole.ADMIN);
        User user3 = new User("User", "User", UserRole.USER);
        User user4 = new User("User", "User", UserRole.USER);

        when(userRepository.findAll()).thenReturn(List.of(user,user2,user3,user4));
        List<UserResponse> userResponses = userService.listAll(user);

        Assertions.assertEquals(4,userResponses.size());
    }

    @Test
    @DisplayName("Verify if user has role user, and throws a exception")
    void ListAllException() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        User user2 = new User("Admin2", "Admin2", UserRole.ADMIN);
        User user3 = new User("User", "User", UserRole.USER);
        User user4 = new User("User", "User", UserRole.USER);

        Assertions.assertThrows(ResponseStatusException.class,() -> userService.listAll(user3));
    }

    @Test
    @DisplayName("Should get a list with all Admins")
    void listAllAdmins() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        User user2 = new User("Admin2", "Admin2", UserRole.ADMIN);
        User user3 = new User("User", "User", UserRole.USER);
        User user4 = new User("User", "User", UserRole.USER);

        when(userRepository.findByRole(UserRole.ADMIN)).thenReturn(List.of(user,user2));
        List<UserResponse> userResponses = userService.listAllAdmins(user);

        Assertions.assertEquals(2,userResponses.size());
    }

    @Test
    @DisplayName("Verify if authenticated user has role User, and throws a exception")
    void listAllAdminsException() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        User user2 = new User("Admin2", "Admin2", UserRole.ADMIN);
        User user3 = new User("User", "User", UserRole.USER);
        User user4 = new User("User", "User", UserRole.USER);

        Assertions.assertThrows(ResponseStatusException.class,() -> userService.listAllAdmins(user3));
    }

    @Test
    @DisplayName("Should get a list with all Users")
    void listAllUsers() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        User user2 = new User("Admin2", "Admin2", UserRole.ADMIN);
        User user3 = new User("User", "User", UserRole.USER);
        User user4 = new User("User", "User", UserRole.USER);

        when(userRepository.findByRole(UserRole.USER)).thenReturn(List.of(user3,user4));
        List<UserResponse> userResponses = userService.listAllUsers(user);

        Assertions.assertEquals(2,userResponses.size());
    }

    @Test
    @DisplayName("Verify if authenticated user has role User, and throws a exception")
    void listAllUsersException() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        User user2 = new User("Admin2", "Admin2", UserRole.ADMIN);
        User user3 = new User("User", "User", UserRole.USER);
        User user4 = new User("User", "User", UserRole.USER);

        Assertions.assertThrows(ResponseStatusException.class,() -> userService.listAllAdmins(user4));
    }

    @Test
    @DisplayName("Should login the user")
    void login() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        LoginRequest loginRequest = new LoginRequest("Admin","Admin");
        String expectedToken = "Token";

        Authentication authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(tokenService.generateToken(user)).thenReturn(expectedToken);
        ResponseEntity<LoginResponse> response = userService.login(loginRequest);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(expectedToken,response.getBody().token());
    }


    @Test
    @DisplayName("Should register a new user")
    void register() {
        RegisterRequest registerRequest = new RegisterRequest("User", "User");
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User user = new User(registerRequest.username(),encryptedPassword,UserRole.USER);

        when(userRepository.findByUsername(any())).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(user);
        ResponseEntity<RegisterResponse> response = userService.register(registerRequest);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(registerRequest.username(),response.getBody().username());
    }


    @Test
    @DisplayName("Verify if the username already exists, and throws a exception")
    void registerException() {
        RegisterRequest registerRequest = new RegisterRequest("User", "User");

        when(userRepository.findByUsername(registerRequest.username())).thenReturn(new User());

        Assertions.assertThrows(ResponseStatusException.class,()-> userService.register(registerRequest));
    }


    @Test
    @DisplayName("Should register a new Admin")
    void registerAdmin() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        RegisterRequest registerRequest = new RegisterRequest("User", "User");
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User admin = new User(registerRequest.username(),encryptedPassword,UserRole.ADMIN);

        when(userRepository.findByUsername(registerRequest.username())).thenReturn(null);
        when(userRepository.save(any())).thenReturn(admin);
        ResponseEntity<RegisterResponse> response = userService.registerAdmin(user,registerRequest);

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(admin.getUsername(),response.getBody().username());
    }

    @Test
    @DisplayName("Verify if authenticated user has role User, and throw a exception")
    void registerAdminException() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        User user2 = new User("Admin2", "Admin2", UserRole.ADMIN);
        User user3 = new User("User", "User", UserRole.USER);
        User user4 = new User("User", "User", UserRole.USER);
        RegisterRequest registerRequest = new RegisterRequest("User", "User");
        User newUser = new User(registerRequest.username(),registerRequest.password(),UserRole.ADMIN);

        Assertions.assertThrows(ResponseStatusException.class,()-> userService.registerAdmin(user3,registerRequest));
    }

    @Test
    @DisplayName("Verify if the username already exists, and throw a exception")
    void registerAdminException2() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        RegisterRequest registerRequest = new RegisterRequest("User", "User");

        when(userRepository.findByUsername(registerRequest.username())).thenReturn(user);

        Assertions.assertThrows(ResponseStatusException.class,()-> userService.registerAdmin(user,registerRequest));
    }

    @Test
    @DisplayName("Should update an existing user")
    void updateUser() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        RegisterRequest registerRequest = new RegisterRequest("User Updated", "User Updated");
        User newUser = new User(registerRequest.username(),registerRequest.password(),UserRole.ADMIN);
    }

    @Test
    @DisplayName("Verify if authenticated user exist, throws an exception if it does not exist")
    void updateUserException() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        RegisterRequest registerRequest = new RegisterRequest("User Updated", "User Updated");
        User newUser = new User(registerRequest.username(),registerRequest.password(),UserRole.ADMIN);

        Assertions.assertThrows(ResponseStatusException.class,()-> userService.updateUser(user.getId(), registerRequest));
    }
}