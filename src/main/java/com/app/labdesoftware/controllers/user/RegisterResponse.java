package com.app.labdesoftware.controllers.user;

import jakarta.validation.constraints.NotNull;

public record RegisterResponse(
        @NotNull String username
) {
}
