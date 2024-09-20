package com.app.ecommerce.controllers.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemRequest(
        @JsonProperty("productID")
        Integer productID,
        @JsonProperty("quantity")
        Integer quantity
) {
}
