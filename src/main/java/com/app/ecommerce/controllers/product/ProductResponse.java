package com.app.ecommerce.controllers.product;

import com.app.ecommerce.entities.Category;
import com.app.ecommerce.entities.Product;
import com.app.ecommerce.enumerations.StatusStock;

import java.util.List;

public record ProductResponse(
        Integer id,
        String name,
        String brand,
        Integer price,
        Category category,
        StatusStock statusStock
) {
    public static List<ProductResponse> fromProduct(List<Product> products){
        return products.stream().map(product -> new ProductResponse(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getCategory(),
                product.getStatusStock()
        )).toList();
    }
}
