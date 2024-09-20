package com.app.ecommerce.services;

import com.app.ecommerce.controllers.authentication.LoginRequest;
import com.app.ecommerce.controllers.authentication.LoginResponse;
import com.app.ecommerce.controllers.user.RegisterRequest;
import com.app.ecommerce.controllers.user.RegisterResponse;
import com.app.ecommerce.controllers.user.UpdateRegisterResponse;
import com.app.ecommerce.controllers.user.UserResponse;
import com.app.ecommerce.entities.User;
import com.app.ecommerce.enumerations.UserRole;
import com.app.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;


    public List<UserResponse> listAll(User user) {
        if (user.getRole().equals(UserRole.USER)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"AUTHENTICATED USER NOT ALLOWED");
        }
        List<User> users = userRepository.findAll();
        return UserResponse.fromUser(users);
    }

    public List<UserResponse> listAllAdmins(User user) {
        if (user.getRole().equals(UserRole.USER)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"AUTHENTICATED USER NOT ALLOWED");
        }
        List<User> users = userRepository.findByRole(UserRole.ADMIN);
        return UserResponse.fromUser(users);
    }


    public List<UserResponse> listAllUsers(User user) {
        if (user.getRole().equals(UserRole.USER)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"AUTHENTICATED USER NOT ALLOWED");
        }
        List<User> users = userRepository.findByRole(UserRole.USER);
        return UserResponse.fromUser(users);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.username(),loginRequest.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new LoginResponse(token);
    }

    public RegisterResponse register(RegisterRequest registerRequest) {
        if(userRepository.findByUsername(registerRequest.username()) != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"USERNAME ALREADY EXISTS, ENTER ANOTHER USERNAME");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User User = new User(registerRequest.username(), encryptedPassword, UserRole.USER);
        userRepository.save(User);
        return new RegisterResponse(User.getUsername());
    }

    public RegisterResponse registerAdmin(User user,RegisterRequest registerRequest){
        if (user.getRole().equals(UserRole.USER)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"AUTHENTICATED USER NOT ALLOWED");
        }
        if(userRepository.findByUsername(registerRequest.username()) != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT,"USERNAME ALREADY EXISTS, ENTER ANOTHER USERNAME");
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User newUser = new User(registerRequest.username(), encryptedPassword, UserRole.ADMIN);
        userRepository.save(newUser);
        return new RegisterResponse(newUser.getUsername());
    }

    public UpdateRegisterResponse update(Integer authenticatedUserId, RegisterRequest registerRequest) {
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "USER NOT FOUND"));
        if (registerRequest.password() != null ) {
            String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
            user.setPassword(encryptedPassword);
        }
        if (registerRequest.username() != null) {
            user.setUsername(registerRequest.username());
        }
        userRepository.save(user);
        return new UpdateRegisterResponse(user.getUsername());
    }
}
