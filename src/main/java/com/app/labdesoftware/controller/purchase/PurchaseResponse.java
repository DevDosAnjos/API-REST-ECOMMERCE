package com.app.labdesoftware.controller.purchase;

import com.app.labdesoftware.entities.Purchase;
import com.app.labdesoftware.enumeration.PaymentMethod;
import com.app.labdesoftware.enumeration.StatusPurchase;

import java.util.List;

public record PurchaseResponse(
        int id,
        Integer clientId,
        Integer cartId,
        Integer total,
        PaymentMethod paymentMethod,
        String deliveryAddress,
        StatusPurchase statusPurchase
) {
    public static List<PurchaseResponse> fromPurchase(List<Purchase> purchases) {
        return purchases.stream().map(purchase -> new PurchaseResponse(
                purchase.getId(),
                purchase.getClientId(),
                purchase.getCart().getId(),
                purchase.getTotal(),
                purchase.getPaymentMethod(),
                purchase.getDeliveryAddress(),
                purchase.getStatusPurchase()
        )).toList();
    }
}
