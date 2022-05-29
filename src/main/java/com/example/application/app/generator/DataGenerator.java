package com.example.application.app.generator;

import com.example.application.backend.data.OrderStatus;
import com.example.application.backend.data.ProductStatus;
import com.example.application.backend.data.entity.*;
import com.example.application.backend.repository.*;
import com.example.application.backend.service.OrderService;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import static com.example.application.backend.data.ProductStatus.*;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(CategoryRepository categoryRepository, ProductRepository productRepository,
                                      TaxRepository taxRepository, CustomerRepository customerRepository, OrderRepository orderRepository) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            int seed = 123;

            logger.info("Generating demo data");

            List<Category> categories = List.of(
                    Category.builder().name("Drinks").build(),
                    Category.builder().name("Food").build(),
                    Category.builder().name("Sub").build()
            );
            categoryRepository.saveAll(categories);


            List<Tax> taxes = List.of(
                    Tax.builder().name("24%").value(0.24).build()
            );
            taxRepository.saveAll(taxes);

            List<Product> products = List.of(
                    Product.builder().name("Coke").category(categories.get(0)).price(2).cost(1)
                            .tax(taxes.get(0)).status(ProductStatus.IN_STOCK).quantity(10)
                            .imageUrl("https://www.coca-cola.gr/content/dam/one/gr/el/product/CCR_330ML.png")
                            .build(),
                    Product.builder().name("Fanta").price(2).cost(1).status(ProductStatus.IN_STOCK).quantity(10).category(categories.get(0)).tax(taxes.get(0)).imageUrl("https://www.coca-cola.gr/content/dam/one/gr/el/product/402324862-ph-bic-fo-500-pet-spiral-30ls-promo-gr-front-w-r2.png").build(),
                    Product.builder().name("French fries").price(2).cost(1).status(ProductStatus.IN_STOCK).quantity(10).category(categories.get(1)).tax(taxes.get(0)).imageUrl("https://thumbor.thedailymeal.com/r19Dr1epeqxTZm6O5CuFj0T0kME=//https://www.thedailymeal.com/sites/default/files/recipe/2018/iStock-687999118_1_.jpg").build(),
                    Product.builder().name("Hot dog").price(2).cost(1).status(ProductStatus.IN_STOCK).quantity(10).category(categories.get(1)).tax(taxes.get(0)).imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fb/Hotdog_-_Evan_Swigart.jpg/1200px-Hotdog_-_Evan_Swigart.jpg").build()
            );
            productRepository.saveAll(products);

            List<Customer> customers = List.of(
                Customer.builder().name("Guest").phone("3001002000").email("guest@test.com").orders(new ArrayList<>()).build()
            );
            customerRepository.saveAll(customers);


            List<Order> orders = List.of(
                Order.builder().customer(customers.get(0)).timeOrdered(LocalDate.now())
                        .products(List.of(
                            OrderProduct.builder().product(products.get(0)).quantity(2).build()
                        ))
                        .status(OrderStatus.NEW).build()
            );
            orderRepository.saveAll(orders);

            logger.info("Generated demo data");
        };
    }

}
