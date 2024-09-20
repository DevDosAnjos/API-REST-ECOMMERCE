package com.app.ecommerce.controllers.purchase;

import com.app.ecommerce.entities.Purchase;
import com.app.ecommerce.enumerations.PaymentMethod;

import java.util.List;

public record PurchaseResponse(
        Integer id,
        String username,
        Integer orderId,
        Integer total,
        PaymentMethod paymentMethod,
        String deliveryAddress
) {
    public static List<PurchaseResponse> fromPurchase(List<Purchase> purchases) {
        return purchases.stream()
                .map(purchase -> new PurchaseResponse(
                purchase.getId(),
                purchase.getUser().getUsername(),
                purchase.getOrder().getId(),
                purchase.getTotal(),
                purchase.getPaymentMethod(),
                purchase.getDeliveryAddress()
        )).toList();
    }

    public static PurchaseResponse fromPurchase(Purchase purchase) {
        return new PurchaseResponse(
                purchase.getId(),
                purchase.getUser().getUsername(),
                purchase.getOrder().getId(),
                purchase.getTotal(),
                purchase.getPaymentMethod(),
                purchase.getDeliveryAddress()
        );
    }
}
