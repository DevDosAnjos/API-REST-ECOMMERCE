package com.app.labdesoftware.services;

import com.app.labdesoftware.controllers.purchase.PurchaseRequest;
import com.app.labdesoftware.controllers.purchase.PurchaseResponse;
import com.app.labdesoftware.entities.Order;
import com.app.labdesoftware.entities.Purchase;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumerations.UserRole;
import com.app.labdesoftware.repositories.OrderRepository;
import com.app.labdesoftware.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private OrderRepository orderRepository;

    public PurchaseResponse getPurchase(Integer id,User user) {
        Optional<Purchase> purchase;
        if (user.getRole().equals(UserRole.ADMIN)){
            purchase = purchaseRepository.findById(id);
        } else {
            purchase = purchaseRepository.findByIdAndUser(id,user);
        }
        if (purchase.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "PURCHASE IS NOT FOUND");
        }
        return new PurchaseResponse(
                purchase.get().getId(),
                purchase.get().getUser().getUsername(),
                purchase.get().getOrder().getId(),
                purchase.get().getTotal(),
                purchase.get().getPaymentMethod(),
                purchase.get().getDeliveryAddress()
        );
    }

    public List<PurchaseResponse> listAllPurchases(User user) {
        List<Purchase> purchases;
        if (user.getRole().equals(UserRole.ADMIN)) {
            purchases = purchaseRepository.findAll();
        } else {
            purchases = purchaseRepository.findAllByUser(user);
        }
        return purchases.stream()
                .map(purchase -> new PurchaseResponse(
                        purchase.getId(),
                        purchase.getUser().getUsername(),
                        purchase.getOrder().getId(),
                        purchase.getTotal(),
                        purchase.getPaymentMethod(),
                        purchase.getDeliveryAddress()
                ))
                .collect(Collectors.toList());
    }

    public PurchaseResponse createPurchase(User user,PurchaseRequest purchaseRequest) {
        Optional<Order> optOrder = orderRepository.findById(purchaseRequest.orderID());
        if (optOrder.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ORDER ID NOT FOUND");
        }
        Order newOrder = optOrder.get();
        if (!newOrder.getUser().equals(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"USER NOT ALLOWED, THE ORDER WAS NOT MADE BY THE AUTHENTICATED USER");
        }
        Purchase purchase = new Purchase(user, newOrder,purchaseRequest.deliveryAddress(),purchaseRequest.paymentMethod());
        purchase.setTotal(purchase.calculateTotal());
        purchaseRepository.save(purchase);
        return PurchaseResponse.fromPurchase(purchase);
    }
}
