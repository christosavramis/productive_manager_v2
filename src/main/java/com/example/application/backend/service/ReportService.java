package com.example.application.backend.service;

import com.example.application.backend.data.OrderStatus;
import com.example.application.backend.data.entity.Customer;
import com.example.application.backend.data.entity.Order;
import com.example.application.backend.data.entity.Product;
import com.example.application.backend.resources.ProductPerformanceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private @Autowired OrderService orderService;
    private @Autowired CustomerService customerService;
    private @Autowired ProductService productService;


    public List<ProductPerformanceResource> getProductsPerformance(Customer customer) {
        List<ProductPerformanceResource> productPerformanceResourceList = new ArrayList<>();
        HashMap<Product, Integer> productCounter = new HashMap<>();
        HashMap<Product, Double> earningsPerProductCounter = new HashMap<>();
        orderService.findAll().stream().filter(order -> {
            OrderStatus orderStatus = order.getStatus();
            return orderStatus.equals(OrderStatus.PAID);
        }).filter(order -> order.getCustomer().equals(customer))
                .forEach(order -> {
                    order.getProducts().forEach(
                            orderProduct -> {
                                Product product = orderProduct.getProduct();
                                int count = 0;
                                double earnings = 0;
                                if (productCounter.containsKey(product)) {
                                    count = productCounter.get(product);
                                    earnings = earningsPerProductCounter.get(product);
                                }
                                count += orderProduct.getQuantity();
                                earnings += + (product.getPrice() - product.getCost()) * orderProduct.getQuantity();
                                productCounter.put(product, count);
                                earningsPerProductCounter.put(product, earnings);
                            }
                    );
                }
        );
        for (Product product:productCounter.keySet()) {
            productPerformanceResourceList.add(new ProductPerformanceResource(product, productCounter.get(product), earningsPerProductCounter.get(product)));
        }
        return productPerformanceResourceList.stream().sorted(Comparator.comparingDouble(ProductPerformanceResource::getTotalEarnings)).collect(Collectors.toList());
    }

    public List<ProductPerformanceResource> getProductsPerformance() {
        List<ProductPerformanceResource> productPerformanceResourceList = new ArrayList<>();
        HashMap<Product, Integer> productCounter = new HashMap<>();
        HashMap<Product, Double> earningsPerProductCounter = new HashMap<>();
        orderService.findAll().stream().filter(order -> {
            OrderStatus orderStatus = order.getStatus();
            return orderStatus.equals(OrderStatus.PAID);
        }).forEach(
                order -> {
                    order.getProducts().forEach(
                            orderProduct -> {
                                Product product = orderProduct.getProduct();
                                int count = 0;
                                double earnings = 0;
                                if (productCounter.containsKey(product)) {
                                    count = productCounter.get(product);
                                    earnings = earningsPerProductCounter.get(product);
                                }
                                count += orderProduct.getQuantity();
                                earnings += + (product.getPrice() - product.getCost()) * orderProduct.getQuantity();
                                productCounter.put(product, count);
                                earningsPerProductCounter.put(product, earnings);
                            }
                    );
                }
        );
        for (Product product:productCounter.keySet()) {
            productPerformanceResourceList.add(new ProductPerformanceResource(product, productCounter.get(product), earningsPerProductCounter.get(product)));
        }
        return productPerformanceResourceList.stream().sorted(Comparator.comparingDouble(ProductPerformanceResource::getTotalEarnings)).collect(Collectors.toList());
    }

    public int getTotalOrdersCount(Customer customer) {
        return (int) orderService.findAll().stream()
                .filter(order -> customer == null || order.getCustomer().equals(customer))
                .filter(order -> !OrderStatus.CANCELLED.equals(order.getStatus()) && !OrderStatus.NEW.equals(order.getStatus()))
                .count();
    }

    public double getTotalEarnings(Customer customer) {
        return orderService.findAll().stream()
                .filter(order -> customer == null || order.getCustomer().equals(customer))
                .filter(order -> !OrderStatus.CANCELLED.equals(order.getStatus()) && !OrderStatus.NEW.equals(order.getStatus()))
                .mapToDouble(Order::getPrice).sum();
    }

    public int getTotalCustomers() {
        return customerService.findAll().size();
    }

    public double getAvgEarningPerOrder(Customer customer) {
        return getTotalEarnings(customer) / getTotalOrdersCount(customer);
    }
}
