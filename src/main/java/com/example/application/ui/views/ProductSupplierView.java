package com.example.application.ui.views;

import com.example.application.backend.data.entities.ProductSupplier;
import com.example.application.backend.service.ProductSupplierService;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.views.forms.ProductSupplierForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@Route(value = "inventory/suppliers", layout = MainLayout.class)
@PageTitle("Suppliers | Productive Manager")
@PermitAll
public class ProductSupplierView extends AbstractCrudView<ProductSupplier> {
    public ProductSupplierView(ProductSupplierService productSupplierService) {
        super("Tax", new ProductSupplierForm(), productSupplierService, ProductSupplier::new);
    }

    @Override
    public void configureGrid() {
        Grid<ProductSupplier> grid = new Grid<>(ProductSupplier.class);
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(ProductSupplier::getId).setHeader("Id");
        grid.addColumn(ProductSupplier::getName).setHeader("Name");
        grid.addColumn(ProductSupplier::getEmail).setHeader("Email");
        grid.addColumn(ProductSupplier::getPhone).setHeader("Phone");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        super.configureGrid(grid);
    }

}
