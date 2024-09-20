package com.app.ecommerce.controllers.authentication;

import com.app.ecommerce.controllers.user.RegisterRequest;
import com.app.ecommerce.controllers.user.RegisterResponse;
import com.app.ecommerce.entities.User;
import com.app.ecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController implements Authentication{

    @Autowired
    private UserService userService;

    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest){
        LoginResponse loginResponse = userService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    public ResponseEntity<RegisterResponse> register(RegisterRequest registerRequest){
        RegisterResponse response = userService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<RegisterResponse> registerAdmin(User user, RegisterRequest registerRequest){
        RegisterResponse response = userService.registerAdmin(user, registerRequest);
        return ResponseEntity.ok(response);
    }

}