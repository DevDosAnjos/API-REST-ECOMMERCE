package com.app.labdesoftware.controllers.order;

import com.app.labdesoftware.entities.Item;
import com.app.labdesoftware.entities.Order;

import java.util.List;

public record OrderResponse(
        Integer id,
        String username,
        List<Item> items
) {
    public static List<OrderResponse> fromOrder(List<Order> orders) {
        return orders.stream().map(order -> new OrderResponse(
                order.getId(),
                order.getUser().getUsername(),
                order.getItems()
        )).toList();
    }
}
