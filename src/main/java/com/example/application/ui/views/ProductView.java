package com.example.application.ui.views;

import com.example.application.backend.data.entity.Product;
import com.example.application.backend.service.CategoryService;
import com.example.application.backend.service.ImageService;
import com.example.application.backend.service.ProductService;
import com.example.application.backend.service.TaxService;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.views.forms.ProductForm;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.stream.Stream;

@Route(value = "inventory/products", layout = MainLayout.class)
@PageTitle("Products | Productive Manager")
public class ProductView extends AbstractCrudView<Product> {

    public ProductView(CategoryService categoryService, TaxService taxService, ProductService productService, ImageService imageService) {
        super("Product", new ProductForm(categoryService::findAll, taxService::findAll, imageService), productService, Product::new);
    }

    @Override
    public void configureGrid() {
        Grid<Product> grid = new Grid<>(Product.class);
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns("id");
        grid.addComponentColumn(product -> {
            String url = product.getImageUrl();
            Image image = new Image(url != null? url : "" , product.getName() + " image is missing");
            image.setWidth(90, Unit.PIXELS);
            image.setHeight(150, Unit.PIXELS);
            return image;
        }).setHeader("Image");
        grid.addColumns("barcode", "name", "cost", "price", "quantity");
        grid.addColumn(category -> category.getCategory() != null ? category.getCategory().getName() : "")
                .setHeader("Category")
                .setSortOrderProvider(category -> Stream.of(new QuerySortOrder("name", category)));
        grid.addColumn(category -> category.getTax() != null ? category.getTax().getName() : "")
                .setHeader("Tax")
                .setSortOrderProvider(category -> Stream.of(new QuerySortOrder("name", category)));

        super.configureGrid(grid);
    }
}
