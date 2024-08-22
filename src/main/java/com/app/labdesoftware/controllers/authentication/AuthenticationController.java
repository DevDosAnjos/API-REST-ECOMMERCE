package com.app.labdesoftware.controllers.authentication;

import com.app.labdesoftware.controllers.user.RegisterRequest;
import com.app.labdesoftware.controllers.user.RegisterResponse;
import com.app.labdesoftware.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController implements Authentication{


    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }


    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest){
        LoginResponse loginResponse = userService.login(loginRequest).getBody();
        return ResponseEntity.ok(loginResponse);
    }

    public ResponseEntity<RegisterResponse> register(RegisterRequest registerRequest){
        RegisterResponse response = userService.register(registerRequest).getBody();
        return ResponseEntity.ok(response);
    }

}