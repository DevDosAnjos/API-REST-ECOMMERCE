package com.app.labdesoftware.controller.authentication.controller;

import com.app.labdesoftware.controller.authentication.DTOs.AuthenticationRequest;
import com.app.labdesoftware.controller.authentication.DTOs.LoginResponse;
import com.app.labdesoftware.controller.authentication.DTOs.RegisterRequest;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumeration.UserRole;
import com.app.labdesoftware.repository.UserRepository;
import com.app.labdesoftware.authentication.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationRequest.username(),authenticationRequest.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest registerRequest){
        if(this.userRepository.findByUsername(registerRequest.username()) != null)
            return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User newUser = new User(registerRequest.username(), encryptedPassword, UserRole.USER);
        this.userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }



}