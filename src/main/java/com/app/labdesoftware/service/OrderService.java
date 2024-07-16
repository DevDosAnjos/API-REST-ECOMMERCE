package com.app.labdesoftware.service;

import com.app.labdesoftware.controller.order.OrderRequest;
import com.app.labdesoftware.controller.order.OrderResponse;
import com.app.labdesoftware.entities.Order;
import com.app.labdesoftware.repository.OrderRepository;
import com.app.labdesoftware.repository.ProductRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public OrderResponse findById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()){
            return new OrderResponse(
                    order.get().getId(),
                    order.get().getProduct(),
                    order.get().getQuantity()
            );
        }
        return null;
    }

    public List<OrderResponse> findAll() {
        List<Order> orders = orderRepository.findAll();
        return OrderResponse.fromOrder(orders);
    }

    public Order create(OrderRequest orderRequest) {
        Order order = Order.from(orderRequest,productRepository);
        return orderRepository.save(order);
    }

    public void update(int id, OrderRequest orderRequest) throws BadRequestException {
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()){
            throw new BadRequestException("ORDER IS NOT FOUND");
        }
        orderRepository.saveAndFlush(order.get());
    }

//    public void delete(int id) {
//        Optional<Order> order = orderRepository.findById(id);
//        if (order.isPresent()){
//            order.get().
//        }
//    }

}
