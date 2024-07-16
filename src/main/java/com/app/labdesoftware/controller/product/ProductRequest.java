package com.app.labdesoftware.controller.product;

import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotNull(message = "NAME IS NOT BE NULL") String name,
        @NotNull(message = "BRAND IS NOT BE NULL") String brand,
        @NotNull(message = "PRICE IS NOT BE NULL") Integer price,
        @NotNull(message = "CATEGORY ID IS NOT BE NULL") int categoryID
){

}
