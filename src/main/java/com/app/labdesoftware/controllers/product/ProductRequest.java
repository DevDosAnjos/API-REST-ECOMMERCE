package com.app.labdesoftware.controllers.product;

import com.app.labdesoftware.enumerations.StatusStock;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be blank")
        @JsonProperty("name")
        String name,
        @NotNull(message = "Brand cannot be null")
        @NotBlank(message = "Brand cannot be blank")
        @JsonProperty("brand")
        String brand,
        @Positive(message = "Price must be greater than 0")
        @JsonProperty("price")
        Integer price,
        @Positive(message = "Category ID must be greater than 0")
        @JsonProperty("categoryID")
        Integer categoryID,
        @NotNull(message = "Status Stock cannot be null")
        @NotBlank(message = "Status Stock cannot be blank")
        @JsonProperty("statusStock")
        StatusStock statusStock
){
}
