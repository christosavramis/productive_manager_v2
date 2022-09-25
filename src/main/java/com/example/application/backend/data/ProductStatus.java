package com.example.application.backend.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
    IN_STOCK("In Stock"), OUT_OF_STOCK("Out of Stock");
    private final String description;

}
