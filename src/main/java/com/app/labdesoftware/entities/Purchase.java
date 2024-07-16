package com.app.labdesoftware.entities;

import com.app.labdesoftware.controller.purchase.PurchaseRequest;
import com.app.labdesoftware.enumeration.PaymentMethod;
import com.app.labdesoftware.enumeration.StatusPurchase;
import com.app.labdesoftware.repository.CartRepository;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity(name = "purchases")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer clientId;
    @OneToOne
    private Cart cart;
    private LocalDateTime createdAt;
    private Integer total;
    private String deliveryAddress;
    private PaymentMethod paymentMethod;
    private StatusPurchase statusPurchase;

    public Purchase() {
    }

    public Purchase(Integer clientId, Cart cart,String deliveryAddress, PaymentMethod paymentMethod, StatusPurchase statusPurchase) {
        this.clientId = clientId;
        this.cart = cart;
        this.createdAt = LocalDateTime.now();
        this.total = this.calculateTotal();
        this.deliveryAddress = deliveryAddress;
        this.paymentMethod = paymentMethod;
        this.statusPurchase = this.changeStatus(statusPurchase);
    }

    public static Purchase from(PurchaseRequest purchaseRequest, CartRepository cartRepository) {
        Integer clientID = purchaseRequest.getUserId();
        Optional<Cart> findCart = cartRepository.findById(purchaseRequest.getCartId());
        Cart cart = findCart.orElseThrow(() -> new RuntimeException("CART NOT FOUND"));
        return new Purchase(
                clientID,
                cart,
                purchaseRequest.deliveryAddress(),
                purchaseRequest.paymentMethod(),
                purchaseRequest.statusPurchase()
        );
    }

    public Integer calculateTotal(){
        Integer total = 0;
        for(Order order : this.cart.getOrders()){
            total += order.getProduct().getPrice() * order.getQuantity();
        }
        return total;
    }

    public StatusPurchase changeStatus(StatusPurchase statusPurchase){
        if(paymentMethod != null){
            statusPurchase = StatusPurchase.APPROVED;
        }
        else {
            statusPurchase = StatusPurchase.PENDING;
        }
        return statusPurchase;
    }
}
