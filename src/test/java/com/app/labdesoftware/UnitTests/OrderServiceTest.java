package com.app.labdesoftware.UnitTests;

import com.app.labdesoftware.controllers.order.ItemRequest;
import com.app.labdesoftware.controllers.order.OrderRequest;
import com.app.labdesoftware.controllers.order.OrderResponse;
import com.app.labdesoftware.entities.*;
import com.app.labdesoftware.enumerations.StatusStock;
import com.app.labdesoftware.enumerations.UserRole;
import com.app.labdesoftware.repositories.OrderRepository;
import com.app.labdesoftware.repositories.ProductRepository;
import com.app.labdesoftware.services.ItemService;
import com.app.labdesoftware.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ItemService itemService;
    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("Should get a order made by the authenticated user")
    void getOrder() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        List<Item> items = new ArrayList<>();
        Order order = new Order(user,items);

        when(orderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        OrderResponse response = orderService.getOrder(user,order.getId());

        Assertions.assertEquals(order.getId(),response.id());
        Assertions.assertEquals("Admin",response.username());
        Assertions.assertEquals(items,response.items());
    }

    @Test
    @DisplayName("Verify if the order exists, if not throws an exception")
    void getOrderException() {
        User user = new User("Admin","Admin", UserRole.ADMIN);

        Assertions.assertThrows(ResponseStatusException.class,()-> orderService.getOrder(user,any()));
    }

    @Test
    @DisplayName("Verity if authenticated user has role User and if the order was made by another user, and throws a exception")
    void getOrderException2() {
        User user = new User("User","User", UserRole.USER);
        List<Item> items = new ArrayList<>();
        Order order = new Order(any(),items);

        Assertions.assertThrows(ResponseStatusException.class,()-> orderService.getOrder(user,order.getId()));
    }

    @Test
    @DisplayName("Should get all orders made by the authenticated user")
    void listAllOrdersUser() {
        User user = new User("User","User", UserRole.USER);
        User user2 = new User("Admin","Admin", UserRole.ADMIN);
        List<Item> items = new ArrayList<>();
        Order order = new Order(user,items);
        Order order2 = new Order(user,items);
        Order order3 = new Order(user2,items);
        Order order4 = new Order(user2,items);

        when(orderRepository.findAllByUser(user)).thenReturn(List.of(order,order2));
        List<OrderResponse> orders = orderService.listAllOrders(user);

        Assertions.assertEquals(2,orders.size());

    }

    @Test
    @DisplayName("Should get all the orders")
    void listAllOrdersAdmin() {
        User user = new User("User","User", UserRole.USER);
        User user2 = new User("Admin","Admin", UserRole.ADMIN);
        List<Item> items = new ArrayList<>();
        Order order = new Order(user,items);
        Order order2 = new Order(user,items);
        Order order3 = new Order(user2,items);
        Order order4 = new Order(user2,items);

        when(orderRepository.findAll()).thenReturn(List.of(order,order2,order3,order4));
        List<OrderResponse> orders = orderService.listAllOrders(user2);

        Assertions.assertEquals(4,orders.size());

    }

    @Test
    @DisplayName("Should create a new order")
    void createOrder() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        Category category = new Category("Test");
        Product product = new Product("Product Test", "Test", 10, category);
        ItemRequest itemRequest = new ItemRequest(product.getId(),2);
        OrderRequest orderRequest = new OrderRequest(List.of(itemRequest));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(orderRepository.save(any())).thenReturn(new Order(user,new ArrayList<>()));
        List<OrderResponse> response = orderService.createOrder(user,orderRequest);

        Assertions.assertEquals(1,response.size());
        Assertions.assertEquals("Admin", response.get(0).username());
    }

    @Test
    @DisplayName("Verify if there are duplicated products in the request, and throws a exception")
    void createOrderException() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        ItemRequest itemRequest1 = new ItemRequest(1, 2);
        ItemRequest itemRequest2 = new ItemRequest(1, 3);
        OrderRequest orderRequest = new OrderRequest(List.of(itemRequest1, itemRequest2));

        Assertions.assertThrows(ResponseStatusException.class, () -> orderService.createOrder(user, orderRequest));
    }


    @Test
    @DisplayName("Verify if the product in the request exists, if not throws a exception")
    void createOrderException2() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        ItemRequest itemRequest = new ItemRequest(1, 2);
        OrderRequest orderRequest = new OrderRequest(List.of(itemRequest));

        when(productRepository.findById(eq(itemRequest.productID()))).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class, () -> orderService.createOrder(user, orderRequest));
    }

    @Test
    @DisplayName("Verify if product is out of stock, and throws a exception")
    void createOrderException3() {
        User user = new User("Admin", "Admin", UserRole.ADMIN);
        Product product = new Product();
        product.setStatusStock(StatusStock.OUT_OF_STOCK);
        ItemRequest itemRequest = new ItemRequest(product.getId(), 2);
        OrderRequest orderRequest = new OrderRequest(List.of(itemRequest));

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Assertions.assertThrows(ResponseStatusException.class, () -> orderService.createOrder(user, orderRequest));
    }
}