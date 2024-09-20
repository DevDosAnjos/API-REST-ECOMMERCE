package com.app.ecommerce.UnitTests;

import com.app.ecommerce.controllers.purchase.PurchaseRequest;
import com.app.ecommerce.controllers.purchase.PurchaseResponse;
import com.app.ecommerce.entities.Item;
import com.app.ecommerce.entities.Order;
import com.app.ecommerce.entities.Purchase;
import com.app.ecommerce.entities.User;
import com.app.ecommerce.enumerations.PaymentMethod;
import com.app.ecommerce.enumerations.UserRole;
import com.app.ecommerce.repositories.OrderRepository;
import com.app.ecommerce.repositories.PurchaseRepository;
import com.app.ecommerce.services.PurchaseService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchaseRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    @DisplayName("Should get a purchase")
    void getPurchaseAdmin() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        List<Item> items = new ArrayList<>();
        Order order = new Order(user,items);
        Purchase purchase = new Purchase(user,order,"Test", PaymentMethod.PIX);

        when(purchaseRepository.findById(user.getId())).thenReturn(Optional.of(purchase));
        PurchaseResponse response = purchaseService.getPurchase(purchase.getId(), user);

        Assertions.assertEquals(purchase.getId(), response.id());
        Assertions.assertEquals(purchase.getUser().getUsername(), response.username());
        Assertions.assertEquals(purchase.getOrder().getId(), response.orderId());
        Assertions.assertEquals(purchase.getTotal(), response.total());
        Assertions.assertEquals(purchase.getPaymentMethod(), response.paymentMethod());
        Assertions.assertEquals(purchase.getDeliveryAddress(), response.deliveryAddress());
    }

    @Test
    @DisplayName("Should get a purchase by the authenticated user")
    void getPurchaseUser() {
        User user = new User("User","User", UserRole.USER);
        List<Item> items = new ArrayList<>();
        Order order = new Order(user,items);
        Purchase purchase = new Purchase(user,order,"Test", PaymentMethod.PIX);

        when(purchaseRepository.findByIdAndUser(purchase.getId(), user)).thenReturn(Optional.of(purchase));
        PurchaseResponse response = purchaseService.getPurchase(purchase.getId(), user);

        Assertions.assertEquals(purchase.getId(), response.id());
        Assertions.assertEquals(purchase.getUser().getUsername(), response.username());
        Assertions.assertEquals(purchase.getOrder().getId(), response.orderId());
        Assertions.assertEquals(purchase.getTotal(), response.total());
        Assertions.assertEquals(purchase.getPaymentMethod(), response.paymentMethod());
        Assertions.assertEquals(purchase.getDeliveryAddress(), response.deliveryAddress());
    }

    @Test
    @DisplayName("Verify if the purchase exists, if not throws an exception")
    void getPurchaseException() {
        User user = new User("Admin","Admin", UserRole.ADMIN);

        when(purchaseRepository.findById(anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,()-> purchaseService.getPurchase(1,user));
    }

    @Test
    @DisplayName("Should get a list of all purchases")
    void listAllPurchasesAdmin() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        List<Item> items = new ArrayList<>();
        Order order = new Order(user,items);
        Purchase purchase = new Purchase(user,order,"Test", PaymentMethod.CREDIT_CARD);
        Purchase purchase2 = new Purchase(user,order,"Test", PaymentMethod.DEBIT_CARD);

        when(purchaseRepository.findAll()).thenReturn(List.of(purchase,purchase2));
        List<PurchaseResponse> purchases = purchaseService.listAllPurchases(user);

        Assertions.assertEquals(2, purchases.size());
    }

    @Test
    @DisplayName("Should get a list of all purchases made by the authenticated user")
    void listAllPurchasesUser() {
        User user = new User("User","User", UserRole.USER);
        List<Item> items = new ArrayList<>();
        Order order = new Order(user,items);
        Purchase purchase = new Purchase(user,order,"Test", PaymentMethod.CREDIT_CARD);
        Purchase purchase2 = new Purchase(user,order,"Test", PaymentMethod.DEBIT_CARD);

        when(purchaseRepository.findAllByUser(user)).thenReturn(List.of(purchase,purchase2));
        List<PurchaseResponse> purchases = purchaseService.listAllPurchases(user);

        Assertions.assertEquals(2, purchases.size());
    }

    @Test
    @DisplayName("Should create a new purchase")
    void createPurchase() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        List<Item> items = new ArrayList<>();
        Order order = new Order(user,items);
        PurchaseRequest purchaseRequest = new PurchaseRequest(order.getId(), PaymentMethod.CREDIT_CARD,"Test");
        Purchase purchase = new Purchase(user,order,"Test", PaymentMethod.CREDIT_CARD);

        when(orderRepository.findById(purchaseRequest.orderID())).thenReturn(Optional.of(order));
        when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchase);
        PurchaseResponse response = purchaseService.createPurchase(user,purchaseRequest);


        Assertions.assertEquals(user.getUsername(),response.username());
        Assertions.assertEquals(user.getId(),response.id());
        Assertions.assertEquals(purchaseRequest.deliveryAddress(),response.deliveryAddress());
        Assertions.assertEquals(purchaseRequest.paymentMethod(),response.paymentMethod());
    }

    @Test
    @DisplayName("Verify if the order exists, if not throws an exception")
    void createPurchaseException() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        PurchaseRequest purchaseRequest = new PurchaseRequest(any(), PaymentMethod.CREDIT_CARD,"Test");

        Assertions.assertThrows(ResponseStatusException.class,()-> purchaseService.createPurchase(user,purchaseRequest));
    }

    @Test
    @DisplayName("Verify if the existing order is from the same authenticated user, if not throws an exception")
    void createPurchaseExceptionUser() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        List<Item> items = new ArrayList<>();
        Order order = new Order(any(),items);
        PurchaseRequest purchaseRequest = new PurchaseRequest(order.getId(), PaymentMethod.CREDIT_CARD,"Test");

        Assertions.assertThrows(ResponseStatusException.class,()-> purchaseService.createPurchase(user,purchaseRequest));
    }
}