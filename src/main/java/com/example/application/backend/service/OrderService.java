package com.example.application.backend.service;

import com.example.application.backend.data.OrderStatus;
import com.example.application.backend.data.entity.Order;
import com.example.application.backend.data.entity.Product;
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
        order.close();

        if (OrderStatus.CLOSED.equals(order.getStatus())) {
            order.getProducts().forEach(orderProduct ->
                    productRepository.findById(orderProduct.getProduct().getId()).ifPresent(product -> {
                        product.setQuantity(product.getQuantity() - orderProduct.getQuantity());
                        productRepository.save(product);
                    })
            );
        }
        return super.save(order);
    }
}
