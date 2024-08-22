package com.app.labdesoftware.controllers.product;

import com.app.labdesoftware.enumerations.StatusStock;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotNull(message = "Brand cannot be null")
        @NotBlank(message = "Brand cannot be blank")
        String brand,
        @Positive(message = "Price must be greater than 0")
        Integer price,
        @Positive(message = "Category ID must be greater than 0")
        Integer categoryID,
        @NotNull(message = "Status Stock cannot be null")
        @NotBlank(message = "Status Stock cannot be blank")
        StatusStock statusStock
){
}
