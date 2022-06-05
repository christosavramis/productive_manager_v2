package com.example.application.app.generator;

import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.OrderStatus;
import com.example.application.backend.data.ProductStatus;
import com.example.application.backend.data.entity.*;
import com.example.application.backend.service.*;
import com.example.application.backend.util.StringUtil;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

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
                    Category.builder().name("Soft Drinks").build(),
                    Category.builder().name("Chips").build()
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
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY + "hell" + ".png")
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
                            .build(),
                    Product.builder().name("Ruffles ketchup").category(categories.get(2)).price(1.40).cost(0.70)
                            .tax(taxes.get(0)).status(ProductStatus.IN_STOCK).quantity(22)
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY + "ruffles_ketchap" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .build(),
                    Product.builder().name("Ruffles Barbeque").category(categories.get(2)).price(1.70).cost(0.88)
                            .tax(taxes.get(0)).status(ProductStatus.IN_STOCK).quantity(51)
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY + "ruffles_barbeque" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .build(),
                    Product.builder().name("Ruffles Salt").category(categories.get(2)).price(1.60).cost(0.90)
                            .tax(taxes.get(0)).status(ProductStatus.IN_STOCK).quantity(30)
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY + "ruffles_salt" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .build()
            );
            productService.saveAll(products);

            List<Customer> customers = List.of(
                Customer.builder().name("Guest").phone("698744233").email("guest@test.com").orders(new ArrayList<>()).build(),
                    Customer.builder().name("John").phone("693755211").email("John@test.com").orders(new ArrayList<>()).build(),
                    Customer.builder().name("Chris").phone("698715212").email("Chris@test.com").orders(new ArrayList<>()).build()
            );
            customerService.saveAll(customers);

            orderService.saveAll(generateRandomOrders(products, customers, List.of(OrderStatus.CANCELED, OrderStatus.CLOSED)));
            logger.info("Generated demo data");
        };
    }

    private List<Order> generateRandomOrders(List<Product> products, List<Customer> customers, List<OrderStatus> statuses){
        int numberOfOrders = 100;
        List<Order> orders = new ArrayList<>();
        long aDay = TimeUnit.DAYS.toMillis(1);
        long now = new Date().getTime();
        Date tendaysago = new Date(now - aDay *100);
        for (int i = 0; i < numberOfOrders; i++) {
            orders.add(Order.builder()
                    .customer((Customer) StringUtil.getRandomOfList(customers))
                    .timeOrdered(between(tendaysago, convertToDateViaInstant(LocalDateTime.now())))
                    .products(getRandomProducts(products))
                    .status((OrderStatus) StringUtil.getRandomOfList(statuses)).build());
        }
        return orders;
    }

    private List<OrderProduct> getRandomProducts(List<Product> products) {
        List<OrderProduct> orderProducts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Product newProduct = (Product) StringUtil.getRandomOfList(products);
            boolean alreadyAdded = orderProducts.stream().anyMatch(orderProduct -> orderProduct.getProduct().equals(newProduct));
            if (!alreadyAdded) {
                orderProducts.add(OrderProduct.builder().product(newProduct).quantity(StringUtil.getRandomNumber(1, 5)).build());
            }
        }
        return orderProducts;
    }

    public static LocalDateTime between(Date startInclusive, Date endExclusive) {
        long startMillis = startInclusive.getTime();
        long endMillis = endExclusive.getTime();
        long randomMillisSinceEpoch = ThreadLocalRandom
                .current()
                .nextLong(startMillis, endMillis);

        return convertToLocalDateViaInstant(new Date(randomMillisSinceEpoch));
    }

    public static LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
