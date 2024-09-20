package com.app.ecommerce.entities;

import com.app.ecommerce.controllers.product.ProductRequest;
import com.app.ecommerce.enumerations.StatusStock;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String brand;
    private Integer price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Enumerated(EnumType.STRING)
    private StatusStock statusStock;

    public Product() {
    }

    public Product(String name, String brand, Integer price, Category category ) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.category = category;
        this.statusStock = StatusStock.IN_STOCK;
    }

    public static Product from(ProductRequest productRequest, Category category){
        return new Product(
                productRequest.name(),
                productRequest.brand(),
                productRequest.price(),
                category
        );
    }
}
