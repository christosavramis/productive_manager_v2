package com.example.application.backend.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Setter @Getter @Builder @AllArgsConstructor @NoArgsConstructor
@Entity
public class OrderProduct extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.DETACH)
    private Product product;

    private int quantity = 1;

    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OrderProduct that = (OrderProduct) o;
        return quantity == that.quantity && Double.compare(that.price, price) == 0 && product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), product, quantity, price);
    }

    public OrderProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.price = quantity * product.getPrice();
    }

    public OrderProduct(OrderProduct orderProduct) {
        this(orderProduct.getProduct(), orderProduct.quantity, orderProduct.getPrice());
    }

    public OrderProduct(Product product) {
        this(product, 1);
    }

    @PreUpdate
    @PrePersist
    public void updatePrice() {
        price = product.getPrice() * quantity;
        price *= product.getTax().getValue() + 1;
    }


    public double getPriceWithoutTax() {
        return product.getPrice() * quantity;
    }

    public double getPriceWithTax() {
        return product.getPrice() * quantity * (1 + product.getTax().getValue());
    }

    public double getOnlyTaxPrice() {
        return getPriceWithoutTax() * product.getTax().getValue();
    }

    public void incrementQuantity() {
        quantity += 1;
        price = quantity * product.getPrice();
    }

    public boolean decrementQuantityOrRemove() {
        if (quantity -1 >= 0) {
            quantity -= 1;
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "product=" + product.getName() +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
