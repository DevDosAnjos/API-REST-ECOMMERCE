package com.app.labdesoftware.controllers.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@Tag(name = "User")
@SecurityRequirement(name = "Bearer Token")
public interface User {

    @Operation(summary = "Get a List of All Users", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "403",description = "Authenticated User not allowed")
    })
    @GetMapping("/all")
    ResponseEntity<List<UserResponse>> listAll();

    @Operation(summary = "Get a List of Users with role Admin", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "403",description = "Authenticated User not allowed")
    })
    @GetMapping("/admins")
    ResponseEntity<List<UserResponse>> listAllAdmins();

    @Operation(summary = "Get a List of Users", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Ok"),
            @ApiResponse(responseCode = "403",description = "Authenticated User not allowed")
    })
    @GetMapping("/users")
    ResponseEntity<List<UserResponse>> listAllUsers();

    @Operation(summary = "Register of a new Admin", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Registration completed successfully"),
            @ApiResponse(responseCode = "403",description = "Authenticated User not allowed"),
            @ApiResponse(responseCode = "409",description = "Username already exist, enter another username")
    })
    @PostMapping("/admin/register")
    ResponseEntity<RegisterResponse> registerAdmin(@RequestBody RegisterRequest registerRequest);

    @Operation(summary = "Updates the info of the currently authenticated User", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Updated successful"),
            @ApiResponse(responseCode = "404",description = "User id not found")
    })
    @PutMapping()
    ResponseEntity<UpdateRegisterResponse> update(@RequestBody RegisterRequest registerRequest);

}
