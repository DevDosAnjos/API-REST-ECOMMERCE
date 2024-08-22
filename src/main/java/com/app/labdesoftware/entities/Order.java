package com.app.labdesoftware.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany
    private List<Item> items;

    public Order() {
    }

    public Order(User user, List<Item> items) {
        this.user = user;
        this.items = items;
    }
}
