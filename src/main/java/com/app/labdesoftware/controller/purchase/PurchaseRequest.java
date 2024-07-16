package com.app.labdesoftware.controller.purchase;

import com.app.labdesoftware.enumeration.PaymentMethod;
import com.app.labdesoftware.enumeration.StatusPurchase;
import jakarta.validation.constraints.NotNull;

public record PurchaseRequest(
        @NotNull Integer userID,
        @NotNull Integer cartID,
        PaymentMethod paymentMethod,
        @NotNull String deliveryAddress,
        StatusPurchase statusPurchase

) {
    public int getUserId(){
       if (userID == null){
           throw new IllegalArgumentException("USERID CANNOT BE NULL");
       }
       return userID;
    }

    public int getCartId(){
        return cartID;
    }
}
