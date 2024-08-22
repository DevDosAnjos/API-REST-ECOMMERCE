package com.app.labdesoftware.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "items")
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Product product;
    private Integer quantity;

    public Item() {
    }

    public Item( Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}
