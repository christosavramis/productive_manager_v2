package com.example.application.ui.views;

import com.example.application.backend.data.entities.Product;
import com.example.application.backend.service.*;
import com.example.application.backend.util.StringUtil;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.views.forms.ProductForm;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.stream.Stream;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Products | Productive Manager")
@PermitAll
public class ProductView extends AbstractCrudView<Product> {

    public ProductView(CategoryService categoryService, TaxService taxService, ProductService productService, ImageService imageService, ProductSupplierService productSupplierService) {
        super("Product", new ProductForm(categoryService::findAll, taxService::findAll, imageService, productSupplierService::findAll), productService, Product::new);
    }

    @Override
    public void configureGrid() {
        Grid<Product> grid = new Grid<>(Product.class);
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(Product::getId).setHeader("Id");
        grid.addComponentColumn(product -> {
            String url = product.getImageUrl();
            Image image = new Image(url != null? url : "" , product.getName() + " image is missing");
//            image.setWidth(90, Unit.PIXELS);
            image.setHeight(150, Unit.PIXELS);
            return image;
        }).setHeader("Image");
        grid.addColumn(Product::getBarcode).setHeader("Barcode");
        grid.addColumn(Product::getName).setHeader("Name");
        grid.addColumn(Product::getQuantity).setHeader("Quantity");
        grid.addColumn(product -> StringUtil.formatPrice(product.getCost())).setHeader("Cost");
        grid.addColumn(product -> StringUtil.formatPrice(product.getPrice())).setHeader("Price");
        grid.addColumn(category -> category.getCategory() != null ? category.getCategory().getName() : "")
                .setHeader("Category")
                .setSortOrderProvider(category -> Stream.of(new QuerySortOrder("name", category)));
        grid.addColumn(product -> product.getTax() != null ? product.getTax().getName() : "")
                .setHeader("Tax")
                .setSortOrderProvider(product -> Stream.of(new QuerySortOrder("name", product)));
        grid.addColumn(product -> product.getSupplier() != null ? product.getSupplier().getName() : "")
                .setHeader("Supplier")
                .setSortOrderProvider(product -> Stream.of(new QuerySortOrder("name", product)));
        LitRenderer<Product> enabledRenderer = LitRenderer.<Product>of(
                        "<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
                .withProperty("icon", enabled -> enabled.isEnabled() ? "check" : "minus").withProperty("color",
                        enabled -> enabled.isEnabled()
                                ? "var(--lumo-primary-text-color)"
                                : "var(--lumo-disabled-text-color)");

        grid.addColumn(enabledRenderer).setHeader("Enabled").setAutoWidth(true);
        super.configureGrid(grid);
    }
}
