package com.app.labdesoftware.controllers.category;

import com.app.labdesoftware.enumerations.StatusCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Status Category cannot be null")
        @NotBlank(message = "Status Category cannot be blank")
        StatusCategory statusCategory
) {
}
