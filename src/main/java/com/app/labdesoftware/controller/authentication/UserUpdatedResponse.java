package com.app.labdesoftware.controller.authentication;

public record UserUpdatedResponse(
        Integer id,
        String username,
        String password
) {
}
