package com.app.labdesoftware.controller.order;

import com.app.labdesoftware.entities.Order;
import com.app.labdesoftware.service.OrderService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/{id}")
    private ResponseEntity<OrderResponse> getOrder(@PathVariable("id")int id ){
        OrderResponse order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/all")
    private ResponseEntity<List<OrderResponse>> listAllOrders(){
    List<OrderResponse> orders = orderService.findAll();
    return ResponseEntity.ok(orders);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest orderRequest){
    Order order = orderService.create(orderRequest);
    return ResponseEntity.ok(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateOrder(@PathVariable("id") int id, @RequestBody OrderRequest orderRequest) throws BadRequestException {
        orderService.update(id,orderRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable("id") int id ){
        orderService.delete(id);
        return ResponseEntity.ok().build();
    }

}
