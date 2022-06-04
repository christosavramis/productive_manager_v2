package com.example.application.ui.views;



import com.example.application.backend.data.entity.Tax;
import com.example.application.backend.service.TaxService;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.views.forms.TaxForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "inventory/taxes", layout = MainLayout.class)
@PageTitle("Taxes | Productive Manager")
public class TaxView extends AbstractCrudView<Tax> {
    public TaxView(TaxService taxService) {
        super("Tax", new TaxForm(), taxService, Tax::new);
    }

    @Override
    public void configureGrid() {
        Grid<Tax> grid = new Grid<>(Tax.class);
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(Tax::getId).setHeader("Id");
        grid.addColumn(Tax::getName).setHeader("Name");
        grid.addColumn(Tax::getValue).setHeader("Value");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        super.configureGrid(grid);
    }

}
