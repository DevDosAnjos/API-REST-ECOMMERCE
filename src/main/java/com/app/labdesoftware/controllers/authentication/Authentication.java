package com.app.labdesoftware.controllers.authentication;

import com.app.labdesoftware.controllers.user.RegisterRequest;
import com.app.labdesoftware.controllers.user.RegisterResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/auth")
@Tag(name = "Authentication")
public interface Authentication {

    @Operation(summary = "Authentication of which User is trying to entry in application", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Login Successful")
    })
    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest);

    @Operation(summary = "Register of a new User", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Registration completed successfully"),
            @ApiResponse(responseCode = "409",description = "Username already exist, enter another username")
    })
    @PostMapping("/register")
    ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest);

    @Operation(summary = "Register of a new Admin", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Registration completed successfully"),
            @ApiResponse(responseCode = "403",description = "Authenticated User not allowed"),
            @ApiResponse(responseCode = "409",description = "Username already exist, enter another username")
    })
    @PostMapping("/admin/register")
    ResponseEntity<RegisterResponse> registerAdmin(@AuthenticationPrincipal com.app.labdesoftware.entities.User user, @RequestBody RegisterRequest registerRequest);
}
