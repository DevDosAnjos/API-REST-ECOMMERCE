package com.app.labdesoftware.controller.category;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull(message = "NAME IS NOT BE NULL") String name
) {
}
