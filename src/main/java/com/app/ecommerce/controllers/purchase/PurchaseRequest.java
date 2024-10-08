package com.app.ecommerce.controllers.purchase;

import com.app.ecommerce.enumerations.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @Positive(message = "Order ID must be greater than 0")
        @JsonProperty("orderID")
        Integer orderID,
        @NotNull(message = "Brand cannot be null")
        @NotBlank(message = "Brand cannot be blank")
        @JsonProperty("paymentMethod")
        PaymentMethod paymentMethod,
        @NotNull(message = "Delivery Address cannot be null")
        @NotBlank(message = "Delivery Address cannot be blank")
        @JsonProperty("deliveryAddress")
        String deliveryAddress
) {
}
