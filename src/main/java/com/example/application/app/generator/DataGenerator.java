package com.example.application.app.generator;

import com.example.application.backend.data.EmployeeRole;
import com.example.application.backend.data.InventoryPolicies;
import com.example.application.backend.data.OrderStatus;
import com.example.application.backend.data.entities.*;
import com.example.application.backend.service.*;
import com.example.application.backend.util.StringUtil;
import com.example.application.security.SecurityService;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.time.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import static com.example.application.backend.service.ImageService.IMAGE_FOLDER_PATH_DISPLAY_GEN;

@SpringComponent
public class DataGenerator {

    @Bean
    public CommandLineRunner loadData(CategoryService categoryService, ProductService productService,
                                      TaxService taxService, CustomerService customerService,
                                      OrderService orderService, ProductSupplierService productSupplierService,
                                      EmployeeService employeeService, SecurityService securityService,
                                      PolicyService policyService) {

        return args -> {

            if (securityService.getAuthenticatedUser() != null) {
                securityService.logout();
            }

            List<Employee> employees = List.of(
                    Employee.builder().name("Chris").username("admin").password("admin").role(EmployeeRole.ADMIN).build(),
                    Employee.builder().name("John").username("john").password("john").role(EmployeeRole.USER).build(),
                    Employee.builder().name("Nick").username("nick").password("nick").role(EmployeeRole.USER).build(),
                    Employee.builder().name("Walter").username("walter").password("walter").role(EmployeeRole.USER).build(),
                    Employee.builder().name("Jack").username("jack").password("jack").role(EmployeeRole.USER).build(),
                    Employee.builder().name("Panos").username("panos").password("panos").role(EmployeeRole.USER).build()
            );

            employeeService.saveAll(employees);


            Logger logger = LoggerFactory.getLogger(getClass());

            logger.info("Generating demo data");

            List<Category> categories = List.of(
                    Category.builder().name("Energy Drinks").build(),
                    Category.builder().name("Soft Drinks").build(),
                    Category.builder().name("Chips").build(),
                    Category.builder().name("Alcohol").build()
            );
            categoryService.saveAll(categories);

            List<ProductSupplier> productSuppliers = List.of(
                    ProductSupplier.builder().email("inventory@Cocacola.com").name("Coca Cola").phone("698744253").build(),
                    ProductSupplier.builder().email("stock@masoutis.com").name("Masoutis").phone("698748494").build(),
                    ProductSupplier.builder().email("stock@lidl.com").name("Lidl").phone("698712733").build()
            );
            productSupplierService.saveAll(productSuppliers);


            List<Tax> taxes = List.of(
                    Tax.builder().name("24%").value(0.24).build()
            );
            taxService.saveAll(taxes);

            List<Product> products = List.of(
                    //Energy Drinks
                    Product.builder().name("Hell 250ml").category(categories.get(0)).price(0.71).cost(0.50)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "Hell" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Monster 500ml").category(categories.get(0)).price(1.39).cost(0.80)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "Monster" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Red Bull 250ml").category(categories.get(0)).price(1.18).cost(0.60)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "RedBull" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Coca Cola 500ml").category(categories.get(1)).price(0.90).cost(0.45)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "CocaCola" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Fanta Orange 500ml").category(categories.get(1)).price(0.74).cost(0.30)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "FantaOrange" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Fanta Lemon 1,5L").category(categories.get(1)).price(1.47).cost(0.80)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "FantaLemon" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Ruffles ketchup").category(categories.get(2)).price(1.40).cost(0.70)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "ruffles_ketchap" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Ruffles Barbeque").category(categories.get(2)).price(1.70).cost(0.88)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "ruffles_barbeque" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Ruffles Salt").category(categories.get(2)).price(1.60).cost(0.90)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "ruffles_salt" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Chivas Regal Scotch").category(categories.get(3)).price(69.80).cost(35.80)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "ChivasRegalScotch" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Serkova Votka").category(categories.get(3)).price(14.25).cost(8.25)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "SerkovaVotka" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build(),
                    Product.builder().name("Johnnie Walker Scotch").category(categories.get(3)).price(43.20).cost(23.20)
                            .tax(taxes.get(0)).enabled(true).quantity(StringUtil.getRandomNumber(100, 300))
                            .imageUrl(IMAGE_FOLDER_PATH_DISPLAY_GEN + "JohnnieWalker" + ".png")
                            .barcode(RandomStringUtils.randomNumeric(14))
                            .supplier((ProductSupplier) StringUtil.getRandomOfList(productSuppliers))
                            .build()
            );
            productService.saveAll(products);

            List<Customer> customers = List.of(
                Customer.builder().name("Guest").phone("698744233").email("guest@test.com").orders(new ArrayList<>()).build(),
                    Customer.builder().name("John").phone("693755211").email("John@test.com").orders(new ArrayList<>()).build(),
                    Customer.builder().name("Chris").phone("698715212").email("Chris@test.com").orders(new ArrayList<>()).build()
            );
            customerService.saveAll(customers);

            List<Order> orderList = generateRandomOrders(products, customers, List.of(OrderStatus.CANCELLED, OrderStatus.PAID));
            orderList.sort(Comparator.comparing(Order::getTimeOrdered).reversed());
            orderService.saveAll(orderList);

            List<Policy> policies = List.of(InventoryPolicies.STOCK_POLICY.getPolicy());
            policyService.saveAll(policies);

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
