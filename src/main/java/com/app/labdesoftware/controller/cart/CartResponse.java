package com.app.labdesoftware.controller.cart;

import com.app.labdesoftware.entities.Cart;
import com.app.labdesoftware.entities.Order;
import com.app.labdesoftware.enumeration.StatusCart;

import java.util.List;

public record CartResponse(
        int id,
        Integer clientId,
        List<Order> orders,
        StatusCart statusCart

) {
    public static List<CartResponse> fromCart(List<Cart> carts) {
        return carts.stream().map(cart -> new CartResponse(
                cart.getId(),
                cart.getClientId(),
                cart.getOrders(),
                cart.getStatusCart()
        )).toList();
    }
}
