package com.example.application.backend.service;

import com.example.application.backend.data.OrderStatus;
import com.example.application.backend.data.entities.Order;
import com.example.application.backend.data.entities.OrderProduct;
import com.example.application.backend.data.entities.Product;
import com.example.application.backend.repository.OrderRepository;
import com.example.application.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends AbstractService<Order> {

    private ProductRepository productRepository;
    public OrderService(OrderRepository repository, ProductRepository productRepository) {
        super(repository);
        this.productRepository = productRepository;
    }

    public Order save(Order order){
        order.calcPrice();

        if (OrderStatus.PAID.equals(order.getStatus())) {
            order.getProducts().forEach(orderProduct ->
                    productRepository.findById(orderProduct.getProduct().getId()).ifPresent(product -> {
                        product.setQuantity(product.getQuantity() - orderProduct.getQuantity());
                        productRepository.save(product);
                    })
            );
        }
        return super.save(order);
    }

    public  Order pay(Order order){
        order.close();
        order.setStatus(OrderStatus.PAID);
        order.getProducts().forEach(orderProduct ->
                productRepository.findById(orderProduct.getProduct().getId()).ifPresent(product -> {
                    product.setQuantity(product.getQuantity() - orderProduct.getQuantity());
                    productRepository.save(product);
                })
        );
        return super.save(order);
    }

    public Order cancel(Order order){
        order.setStatus(OrderStatus.CANCELLED);
        order.getProducts().forEach(orderProduct ->
                productRepository.findById(orderProduct.getProduct().getId()).ifPresent(product -> {
                    product.setQuantity(product.getQuantity() + orderProduct.getQuantity());
                    productRepository.save(product);
                })
        );
        return super.save(order);
    }

    public String getQuantityWarning(Order order) {
        if (OrderStatus.PAID.equals(order.getStatus()) || OrderStatus.CANCELLED.equals(order.getStatus())) {
            return null;
        }

        for (OrderProduct orderProduct : order.getProducts()) {
            Product product = productRepository.findById(orderProduct.getProduct().getId()).orElse(null);
            if (product != null && product.getQuantity() < orderProduct.getQuantity()) {
                return product.getName() + " is not available in the required quantity of " + orderProduct.getQuantity();
            }
        }

        return null;
    }

}
