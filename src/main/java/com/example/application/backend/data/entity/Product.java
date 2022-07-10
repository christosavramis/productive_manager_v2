package com.example.application.backend.data.entity;




import com.example.application.backend.data.ProductStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class Product extends AbstractEntity {

//    @NotBlank(message = "Product name is required")
    @Size(max = 255)
    @Column(unique = true)
    private String name;

//    @Min(value = 0, message = "Minimum price is 0")
    private double price;

//    @Min(value = 0, message = "Minimum price is 0")
    private double cost;

//    @Min(value = 0, message = "Minimum quantity is 0")
    private int quantity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Please select a status")
    private ProductStatus status;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn
    private Category category;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn
    private ProductSupplier supplier;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    private Tax tax;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String imageUrl;

    private boolean markedForDelete = false;

    private String barcode;

    public Product copy() {
        Product product = Product.builder()
                .name(name)
                .price(price)
                .cost(cost)
                .quantity(quantity)
                .status(status)
                .category(category)
                .tax(tax)
                .imageUrl(imageUrl)
                .barcode(barcode)
                .markedForDelete(markedForDelete)
                .build();
        product.setId(getId());
        product.setVersion(getVersion());
        return product;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                ", quantity=" + quantity +
                ", status=" + status +
                ", category=" + category +
                ", tax=" + tax +
                ", imageUrl='" + imageUrl + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }
}
