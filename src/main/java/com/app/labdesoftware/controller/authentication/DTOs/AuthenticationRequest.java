package com.app.labdesoftware.controller.authentication.DTOs;

public record AuthenticationRequest(
        String username,
        String password
) {
}
