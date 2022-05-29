package com.example.application.app.generator;

import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.OrderStatus;
import com.example.application.backend.data.ProductStatus;
import com.example.application.backend.data.entity.*;
import com.example.application.backend.repository.*;
import com.example.application.backend.service.*;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import static com.example.application.backend.data.ProductStatus.*;
import static com.example.application.backend.service.ImageService.IMAGE_FOLDER_PATH_DISPLAY;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(CategoryService categoryService, ProductService productService,
                                      TaxService taxService, CustomerService customerService,
                                      OrderService orderService) {

        return args -> {
            Logger logger = LoggerFactory.getLogger(getClass());

            logger.info("Generating demo data");

            List<Category> categories = List.of(
                    Category.builder().name("Energy Drinks").build(),
                    Category.builder().name("Soft Drinks").build()
            );
            categoryService.saveAll(categories);


            List<Tax> taxes = List.of(
                    Tax.builder().name("24%").value(0.24).build()
            );
            taxService.saveAll(taxes);

            List<Product> products = List.of(
                    //Energy Drinks
                    Product.builder().name("Hell 250ml").category(categories.get(0)).price(0.71).cost(0.50)
                            .tax(taxes.get(0)).status(ProductStatus.IN_STOCK).quantity(10)
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY + "Hell" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .build(),
                    Product.builder().name("Monster 500ml").category(categories.get(0)).price(1.39).cost(0.80)
                            .tax(taxes.get(0)).status(ProductStatus.IN_STOCK).quantity(10)
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY + "Monster" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .build(),
                    Product.builder().name("Red Bull 250ml").category(categories.get(0)).price(1.18).cost(0.60)
                            .tax(taxes.get(0)).status(ProductStatus.IN_STOCK).quantity(10)
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY + "RedBull" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .build(),
                    Product.builder().name("Coca Cola 500ml").category(categories.get(1)).price(0.90).cost(0.45)
                            .tax(taxes.get(0)).status(ProductStatus.IN_STOCK).quantity(10)
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY + "CocaCola" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .build(),
                    Product.builder().name("Fanta Orange 500ml").category(categories.get(1)).price(0.74).cost(0.30)
                            .tax(taxes.get(0)).status(ProductStatus.IN_STOCK).quantity(10)
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY + "FantaOrange" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .build(),
                    Product.builder().name("Fanta Lemon 1,5L").category(categories.get(1)).price(1.47).cost(0.80)
                            .tax(taxes.get(0)).status(ProductStatus.IN_STOCK).quantity(10)
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY + "FantaLemon" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .build()
            );
            productService.saveAll(products);

            List<Customer> customers = List.of(
                Customer.builder().name("Guest").phone("3001002000").email("guest@test.com").orders(new ArrayList<>()).build()
            );
            customerService.saveAll(customers);


            List<Order> orders = List.of(
                Order.builder().customer(customers.get(0)).timeOrdered(LocalDate.now())
                        .products(List.of(
                            OrderProduct.builder().product(products.get(0)).quantity(2).build()
                        ))
                        .status(OrderStatus.NEW).build()
            );
            orderService.saveAll(orders);

            logger.info("Generated demo data");
        };
    }

}
