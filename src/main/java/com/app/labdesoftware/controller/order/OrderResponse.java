package com.app.labdesoftware.controller.order;

import com.app.labdesoftware.entities.Order;
import com.app.labdesoftware.entities.Product;

import java.util.List;

public record OrderResponse(
        int id,
        Product product,
        Integer quantity
) {

    public static List<OrderResponse> fromOrder(List<Order> orders) {
        return orders.stream().map(item -> new OrderResponse(
                item.getId(),
                item.getProduct(),
                item.getQuantity()
        )).toList();
    }
}
