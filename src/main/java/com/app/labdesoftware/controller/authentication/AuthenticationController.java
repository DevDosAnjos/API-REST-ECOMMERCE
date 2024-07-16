package com.app.labdesoftware.controller.authentication;

import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.repository.UserRepository;
import com.app.labdesoftware.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        if (authService.register(request.username(), request.password())){
            return ResponseEntity.ok(new RegisterResponse(request.username()));
        }else {
            return ResponseEntity.status(409).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>login(@RequestBody User user, HttpServletResponse response) {
        User foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())){
            response.setHeader("Location", "/");
            response.setStatus(302);
            return  ResponseEntity.ok(new LoginResponse(foundUser.getId(), foundUser.getUsername()));
        }else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateUser(@PathVariable("id") int id, @RequestBody User user) throws BadRequestException {
        authService.update(id,user);
        return ResponseEntity.ok(new UserUpdatedResponse(user.getId(), user.getUsername(), user.getPassword()));
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }

}