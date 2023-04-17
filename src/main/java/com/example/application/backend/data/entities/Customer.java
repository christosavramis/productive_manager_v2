package com.example.application.backend.data.entities;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Customer extends AbstractEntity {

    private String name;

    private String email;

    private String phone;

    @OneToMany(targetEntity = Order.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<Order>();

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", orders=" + orders +
                '}';
    }
}
