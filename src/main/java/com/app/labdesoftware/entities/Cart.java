package com.app.labdesoftware.entities;

import com.app.labdesoftware.controller.cart.CartRequest;
import com.app.labdesoftware.enumeration.StatusCart;
import com.app.labdesoftware.repository.OrderRepository;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity(name = "carts")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer clientId;
    @OneToMany
    private List<Order> orders;
    private StatusCart statusCart;

    public Cart() {
    }

    public Cart(Integer clientId, List<Order> orders) {
        this.clientId = clientId;
        this.orders = orders;
        this.statusCart = StatusCart.ACTIVE;

    }

    public static Cart from(CartRequest cartRequest, OrderRepository orderRepository) {
        Integer clientId = cartRequest.id();
        List<Order> orders = new ArrayList<>();
        for (Integer orderId: cartRequest.orders()){
            Optional<Order> order = orderRepository.findById(orderId);
            if (order.isPresent()){
                orders.add(order.get());
            }
        }
        return new Cart(
                clientId,
                orders
        );
    }
}
