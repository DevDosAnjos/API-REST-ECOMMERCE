package com.app.labdesoftware.controllers.order;

public record ItemRequest(
        Integer productID,
        Integer quantity
) {
}
