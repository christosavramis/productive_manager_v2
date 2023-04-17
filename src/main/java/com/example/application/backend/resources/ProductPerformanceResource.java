package com.example.application.backend.resources;

import com.example.application.backend.data.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPerformanceResource {
    private Product product;
    private int count;
    private double totalEarnings; // cost - price

}
