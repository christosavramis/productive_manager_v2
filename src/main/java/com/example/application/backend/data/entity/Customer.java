package com.example.application.backend.data.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Customer extends AbstractEntity {

    private String name;

    @Email
    private String email;

    @Pattern(regexp = "^(\\+\\d+)?([ -]?\\d+){4,14}$", message = "Phone not valid")
    private String phone;

    @OneToMany(targetEntity = Order.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Order> orders = new ArrayList<Order>();
}
