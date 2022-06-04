package com.example.application.ui.views;

import com.example.application.backend.data.entity.Category;
import com.example.application.backend.service.CategoryService;
import com.example.application.ui.MainLayout;
import com.example.application.ui.crud.AbstractCrudView;
import com.example.application.ui.views.forms.CategoryForm;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "inventory/categories", layout = MainLayout.class)
@PageTitle("Categories | Productive Manager")
public class CategoryView extends AbstractCrudView<Category> {

    public CategoryView(CategoryService categoryService) {
        super("Category", new CategoryForm(), categoryService, Category::new);
    }

    @Override
    public void configureGrid() {
        Grid<Category> grid = new Grid<>(Category.class);
        grid.addClassNames("product-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(Category::getId).setHeader("Id");
        grid.addColumn(Category::getName).setHeader("Name");
        super.configureGrid(grid);
    }
}
