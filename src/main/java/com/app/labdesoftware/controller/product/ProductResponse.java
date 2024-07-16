package com.app.labdesoftware.controller.product;

import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.entities.Product;
import com.app.labdesoftware.enumeration.Stock;

import java.util.List;

public record ProductResponse(
        int id,
        String name,
        String brand,
        Integer price,
        Category category,
        Stock stock
) {
    public static List<ProductResponse> fromProduct(List<Product> products){
        return products.stream().map(product -> new ProductResponse(
                product.getId(),
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getCategory(),
                product.getStock()
        )).toList();
    }
}
