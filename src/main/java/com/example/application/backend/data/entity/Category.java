package com.example.application.backend.data.entity;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Setter @Getter @Builder @AllArgsConstructor
@Entity
public class Category extends AbstractEntity {

    public Category(){}

    @Column(nullable = false, unique=true)
    @NotEmpty
    private String name;

    @OneToMany(targetEntity = Product.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        products.add(product);
        product.setCategory(this);
    }

}
