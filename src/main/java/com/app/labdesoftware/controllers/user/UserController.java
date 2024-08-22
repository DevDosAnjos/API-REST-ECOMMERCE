package com.app.labdesoftware.controllers.user;

import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements com.app.labdesoftware.controllers.user.User {

    @Autowired
    private UserService userService;

    public ResponseEntity<List<UserResponse>> listAll(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserResponse> responses = userService.listAll(user);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<List<UserResponse>> listAllAdmins(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserResponse> responses = userService.listAllAdmins(user);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<List<UserResponse>> listAllUsers(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserResponse> responses = userService.listAllUsers(user);
        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<RegisterResponse> registerAdmin(RegisterRequest registerRequest){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RegisterResponse response = userService.registerAdmin(user,registerRequest).getBody();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<UpdateRegisterResponse> update(RegisterRequest registerRequest){
        Integer authenticatedUserId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        UpdateRegisterResponse response = userService.updateUser(authenticatedUserId,registerRequest).getBody();
        return ResponseEntity.ok(response);
    }
}
