package com.app.labdesoftware.controllers.user;

import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements com.app.labdesoftware.controllers.user.User {

    @Autowired
    private UserService userService;

    public ResponseEntity<List<UserResponse>> listAll(User user){
        List<UserResponse> responses = userService.listAll(user);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<List<UserResponse>> listAllAdmins(User user){
        List<UserResponse> responses = userService.listAllAdmins(user);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<List<UserResponse>> listAllUsers(User user){
        List<UserResponse> responses = userService.listAllUsers(user);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<UpdateRegisterResponse> update(User user, RegisterRequest registerRequest){
        UpdateRegisterResponse response = userService.update(user.getId(), registerRequest);
        return ResponseEntity.ok(response);
    }
}
