package com.app.labdesoftware.controller.cart;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CartRequest(
    @NotNull Integer userID,
    @NotNull List<Integer> orders
) {

    public Integer id() {
        return this.userID;
    }
}
