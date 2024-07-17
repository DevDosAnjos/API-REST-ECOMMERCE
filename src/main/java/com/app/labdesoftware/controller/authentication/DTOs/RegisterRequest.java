package com.app.labdesoftware.controller.authentication.DTOs;

import com.app.labdesoftware.enumeration.UserRole;

public record RegisterRequest(
        String username,
        String password
) {
}
