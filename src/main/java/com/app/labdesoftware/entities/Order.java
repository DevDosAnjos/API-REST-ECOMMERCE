package com.app.labdesoftware.entities;

import com.app.labdesoftware.controller.order.OrderRequest;
import com.app.labdesoftware.repository.ProductRepository;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "Orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    private Product product;
    private Integer quantity;

    public Order() {
    }

    public Order(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public static Order from(OrderRequest orderRequest, ProductRepository productRepository) {
       Integer productId = orderRequest.productID();
       Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("PRODUCT NOT FOUND"));
        return new Order(
                product,
                orderRequest.quantity());
    }
}
