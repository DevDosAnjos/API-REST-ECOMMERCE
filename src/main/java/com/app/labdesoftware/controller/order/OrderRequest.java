package com.app.labdesoftware.controller.order;

import jakarta.validation.constraints.NotNull;

public record OrderRequest(
        @NotNull Integer productID,
        @NotNull Integer quantity
) {
}
