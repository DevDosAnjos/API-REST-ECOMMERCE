package com.app.labdesoftware.controllers.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotNull(message = "Items cannot be null")
        @NotBlank(message = "Items cannot be blank")
        List<ItemRequest> items
) {
}
