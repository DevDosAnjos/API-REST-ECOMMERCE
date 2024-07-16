package com.app.labdesoftware.controller.authentication;

import jakarta.validation.constraints.NotNull;

public record RegisterRequest(
        @NotNull String username,
        @NotNull String password
) {
}
