package com.app.labdesoftware.services;

import com.app.labdesoftware.controllers.order.ItemRequest;
import com.app.labdesoftware.controllers.order.OrderRequest;
import com.app.labdesoftware.controllers.order.OrderResponse;
import com.app.labdesoftware.entities.Item;
import com.app.labdesoftware.entities.Order;
import com.app.labdesoftware.entities.Product;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumerations.StatusStock;
import com.app.labdesoftware.enumerations.UserRole;
import com.app.labdesoftware.repositories.OrderRepository;
import com.app.labdesoftware.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ItemService itemService;
    @Autowired
    private ProductRepository productRepository;

    public OrderResponse getOrder(User user,Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"ORDER ID NOT FOUND");
        }
        if (user.getRole().equals(UserRole.USER) && !order.get().getUser().equals(user)) {
           throw new ResponseStatusException(HttpStatus.FORBIDDEN,"USER NOT ALLOWED");
        }
        return new OrderResponse(
                order.get().getId(),
                order.get().getUser().getUsername(),
                order.get().getItems()
        );
    }

    public List<OrderResponse> listAllOrders(User user) {
        List<Order> orders;
        if (user.getRole().equals(UserRole.USER)){
            orders = orderRepository.findAllByUser(user);
        }
        else {
            orders = orderRepository.findAll();
        }
        return OrderResponse.fromOrder(orders);
    }

    public List<OrderResponse> createOrder(User user, OrderRequest orderRequest) {
        List<Integer> productIds = new ArrayList<>();
        List<Item> listItems = new ArrayList<>();
        for (ItemRequest itemRequest : orderRequest.items()){
            if (productIds.contains(itemRequest.productID())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"DUPLICATE ProductID: " + itemRequest.productID());
            }
            productIds.add(itemRequest.productID());
            Product product = productRepository.findById(itemRequest.productID())
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"PRODUCT ID NOT FOUND:" + itemRequest.productID()));
            if (product.getStatusStock() == StatusStock.OUT_OF_STOCK){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"PRODUCT OUT OF STOCK: " + itemRequest.productID());
            }
        }
        for (ItemRequest itemRequest : orderRequest.items()){
            Product product = productRepository.findById(itemRequest.productID())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PRODUCT ID NOT FOUND: " + itemRequest.productID()));
            Item newItem = new Item();
            newItem.setProduct(product);
            newItem.setQuantity(itemRequest.quantity());
            itemService.create(newItem);
            listItems.add(newItem);
        }
        Order order = new Order(user, listItems);
        orderRepository.save(order);
        return OrderResponse.fromOrder(List.of(order));
    }
}
