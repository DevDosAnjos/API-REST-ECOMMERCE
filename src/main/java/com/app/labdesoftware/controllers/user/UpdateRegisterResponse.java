package com.app.labdesoftware.controllers.user;

import jakarta.validation.constraints.NotNull;

public record UpdateRegisterResponse(
        @NotNull String usernameUpdated
) {
}
