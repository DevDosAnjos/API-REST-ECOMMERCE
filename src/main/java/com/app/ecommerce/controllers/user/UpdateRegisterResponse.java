package com.app.ecommerce.controllers.user;

import jakarta.validation.constraints.NotNull;

public record UpdateRegisterResponse(
        @NotNull String usernameUpdated
) {
}
