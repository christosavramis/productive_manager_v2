package com.example.application.ui.views;



import com.example.application.backend.data.entity.Customer;
import com.example.application.backend.data.entity.Product;
import com.example.application.backend.resources.ProductPerformanceResource;
import com.example.application.backend.service.CustomerService;
import com.example.application.backend.service.ReportService;
import com.example.application.backend.util.StringUtil;
import com.example.application.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@Route(value = "reports/product", layout = MainLayout.class)
@PageTitle("Statistics | Productive Manager")
@PermitAll
public class ReportProductView extends VerticalLayout {
    private ComboBox<Customer> selectCustomer = new ComboBox<>("Customer");
    private Button clearSelectCustomer = new Button("Clear");
    private Grid<ProductPerformanceResource> grid = new Grid<>(ProductPerformanceResource.class);

    private Span totalOrderSpan = new Span();
    private Span totalRevenueSpan = new Span();
    private Span totalCustomersSpan = new Span();
    private Span averageEarningSpan = new Span();
    private ReportService reportService;
    public ReportProductView(ReportService reportService, CustomerService customerService) {
        this.reportService = reportService;
        setSizeFull();
        grid.addClassNames("product-grid");
        grid.setColumns();
        grid.addColumn(productPerformanceResource -> productPerformanceResource.getProduct().getName()).setHeader("Name");
        grid.addComponentColumn(productPerformanceResource -> {
            Product product = productPerformanceResource.getProduct();
            String url = product.getImageUrl();
            Image image = new Image(url != null? url : "" , product.getName() + " image             is missing");
//            image.setWidth(90, Unit.PIXELS);
            image.setHeight(150, Unit.PIXELS);
            return image;
        }).setHeader("Image");
        grid.addColumn(ProductPerformanceResource::getCount).setHeader("Count");
        grid.addColumn(productPerformanceResource -> StringUtil.formatPrice(productPerformanceResource.getTotalEarnings())).setHeader("TotalEarnings");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setHeight(50, Unit.PERCENTAGE);
        grid.setItems(reportService.getProductsPerformance());
        VerticalLayout statistics = new VerticalLayout();
        statistics.add(totalOrderSpan);
        statistics.add(totalCustomersSpan);
        statistics.add(totalRevenueSpan);
        statistics.add(averageEarningSpan);
        setupLabels(null);
        statistics.add(selectCustomer);
        statistics.add(clearSelectCustomer);
        add(statistics);
        add(grid);

        selectCustomer.setItems(customerService.findAll());
        selectCustomer.setItemLabelGenerator(Customer::getName);
        selectCustomer.addValueChangeListener(event -> {
            Customer customer = event.getValue();
            if (customer != null) {
                grid.setItems(reportService.getProductsPerformance(customer));
                setupLabels(customer);
            } else {
                grid.setItems(reportService.getProductsPerformance());
            }
        });
        clearSelectCustomer.addClickListener(e -> {
            selectCustomer.clear();
            grid.setItems(reportService.getProductsPerformance());
            setupLabels(null);
        });
    }

    private void setupLabels(Customer customer) {
        totalCustomersSpan.setText("Total Customers: " + reportService.getTotalCustomers());
        totalOrderSpan.setText("Total Orders: " + reportService.getTotalOrdersCount(customer));
        totalRevenueSpan.setText("Total Revenue: " + StringUtil.formatPrice(reportService.getTotalEarnings(customer)));
        averageEarningSpan.setText("Avg Earnings per Order: " + StringUtil.formatPrice(reportService.getAvgEarningPerOrder(customer)));
    }
}
