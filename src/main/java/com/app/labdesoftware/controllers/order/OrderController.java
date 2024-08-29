package com.app.labdesoftware.controllers.order;

import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController implements Order{

    @Autowired
    private OrderService orderService;

    public ResponseEntity<OrderResponse> get(User user,Integer id) {
        OrderResponse orderResponse = orderService.getOrder(user,id);
        return ResponseEntity.ok(orderResponse);
    }

    public ResponseEntity<List<OrderResponse>> listAll(User user){
        List<OrderResponse> orderResponses = orderService.listAllOrders(user);
        return ResponseEntity.ok(orderResponses);
   }

    public ResponseEntity<List<OrderResponse>> create(User user,OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(user,orderRequest));
    }
}
