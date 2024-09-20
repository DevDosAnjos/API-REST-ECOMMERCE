package com.app.ecommerce.controllers.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequest(
        @NotNull(message = "Items cannot be null")
        @Size(min = 1, message = "Items cannot be empty")
        @JsonProperty("items")
        List<ItemRequest> items
) {
}
