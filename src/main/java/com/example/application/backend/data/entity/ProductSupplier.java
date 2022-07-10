package com.example.application.backend.data.entity;

import lombok.*;

import javax.persistence.Entity;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor @Entity
public class ProductSupplier extends AbstractEntity {
    private String name;
    private String email;
    private String phone;

    @Override
    public String toString() {
        return "ProductSupplier{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
