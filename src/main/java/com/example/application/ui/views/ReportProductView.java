package com.example.application.ui.views;



import com.example.application.backend.data.entity.Product;
import com.example.application.backend.resources.ProductPerformanceResource;
import com.example.application.backend.service.ReportService;
import com.example.application.backend.util.StringUtil;
import com.example.application.ui.MainLayout;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "reports/product", layout = MainLayout.class)
@PageTitle("Statistics | Productive Manager")
public class ReportProductView extends VerticalLayout {

    public ReportProductView(ReportService reportService) {
        setSizeFull();
        Grid<ProductPerformanceResource> grid = new Grid<>(ProductPerformanceResource.class);
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
        statistics.add(new Span("Total Orders: " + reportService.getTotalOrdersCount()));
        statistics.add(new Span("Total Customers: " + reportService.getTotalCustomers()));
        statistics.add(new Span("Total Earnings: " + StringUtil.formatPrice(reportService.getTotalEarnings())));
        statistics.add(new Span("Avg Earnings per Order: " + StringUtil.formatPrice(reportService.getAvgEarningPerOrder())));
        add(statistics);
        add(grid);
    }


}
