package com.app.labdesoftware.controllers.purchase;

import com.app.labdesoftware.enumerations.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @Positive(message = "Order ID must be greater than 0")
        Integer orderID,
        @NotNull(message = "Brand cannot be null")
        @NotBlank(message = "Brand cannot be blank")
        PaymentMethod paymentMethod,
        @NotNull(message = "Delivery Address cannot be null")
        @NotBlank(message = "Delivery Address cannot be blank")
        String deliveryAddress
) {
    public Integer getCartId(){
        return orderID;
    }
}
