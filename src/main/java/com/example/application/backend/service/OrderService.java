package com.example.application.backend.service;

import com.example.application.backend.data.entity.Order;
import com.example.application.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends AbstractService<Order> {

    public OrderService(OrderRepository repository) {
        super(repository);
    }

    public Order save(Order order){
        order.calcPrice();
        return super.save(order);
    }
}
