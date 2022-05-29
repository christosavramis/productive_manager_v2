package com.example.application.backend.data.entity;

import com.example.application.backend.data.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name = "order_info")
public class Order extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn
    private Customer customer;

    private LocalDate timeOrdered;

    private OrderStatus status;

    private double price;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn
    private List<OrderProduct> products = new ArrayList<>();

    @PrePersist
    @PreUpdate
    public void calcPrice() {
        products.forEach(OrderProduct::updatePrice);
        price = products.stream().mapToDouble(OrderProduct::getPrice).sum();
    }

    public void close(){
        if (timeOrdered == null){
            timeOrdered = LocalDate.now();
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "customer=" + customer +
                ", timeOrdered=" + timeOrdered +
                ", status=" + status +
                ", price=" + price +
                ", products=" + products +
                '}';
    }
}
