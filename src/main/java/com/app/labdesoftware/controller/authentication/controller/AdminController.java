package com.app.labdesoftware.controller.authentication.controller;

import com.app.labdesoftware.controller.authentication.DTOs.RegisterRequest;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumeration.UserRole;
import com.app.labdesoftware.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository repository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest registerRequest){
        if(this.repository.findByUsername(registerRequest.username()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequest.password());
        User newUser = new User(registerRequest.username(), encryptedPassword, UserRole.ADMIN);

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

//    @PutMapping("/user/{id}")
//    public ResponseEntity updateRole(@)

}
