package com.app.ecommerce.entities;

import com.app.ecommerce.enumerations.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "purchases")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    private Order order;
    private LocalDateTime createdAt;
    private Integer total;
    private String deliveryAddress;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public Purchase() {
    }

    public Purchase(User user, Order order, String deliveryAddress, PaymentMethod paymentMethod) {
        this.user = user;
        this.order = order;
        this.createdAt = LocalDateTime.now();
        this.total = this.calculateTotal();
        this.deliveryAddress = deliveryAddress;
        this.paymentMethod = paymentMethod;
    }

    public Integer calculateTotal(){
        Integer total = 0;
        for(Item item : this.order.getItems()){
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
}
