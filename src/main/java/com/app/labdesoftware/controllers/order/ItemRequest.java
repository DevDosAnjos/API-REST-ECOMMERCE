package com.app.labdesoftware.controllers.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemRequest(
        @JsonProperty("productID")
        Integer productID,
        @JsonProperty("quantity")
        Integer quantity
) {
}
