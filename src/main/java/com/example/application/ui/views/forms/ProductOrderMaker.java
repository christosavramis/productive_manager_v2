package com.example.application.ui.views.forms;

import com.example.application.backend.data.entity.OrderProduct;
import com.example.application.backend.data.entity.Product;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;

class ProductOrderMaker extends VerticalLayout {
    private final Grid<OrderProduct> orderProductGrid = new Grid<>();
    private List<OrderProduct> orderProducts;
    private final Grid<Product> productGrid = new Grid<>();
    private Div productTotal = new Div();
    private Div taxTotal = new Div();
    private Div orderTotal = new Div();

    ProductOrderMaker(List<Product> availableProducts) {
        setupProductGrid(availableProducts);
        setupOrderProductGrid();

        VerticalLayout orderingLayout = new VerticalLayout();
        orderingLayout.setSizeFull();
        orderingLayout.add(new H2("Ordered products"), addToOrderField(), productGrid, orderProductGrid, new VerticalLayout(productTotal, taxTotal, orderTotal));
        add(orderingLayout);
//        getStyle().set("background-color", "red");
    }

    private void setupProductGrid(List<Product> availableProducts) {
        productGrid.addColumn(Product::getName).setHeader("Product Name");
        productGrid.addComponentColumn(product -> {
            String url = product.getImageUrl();
            Image image = new Image(url != null? url : "" , product.getName() + " image is missing");
            image.setWidth(50, Unit.PIXELS);
            image.setHeight(50, Unit.PIXELS);
            return image;
        }).setHeader("Image");
        productGrid.addComponentColumn(product -> {
            Button addToOrderButton = new Button("+");
            addToOrderButton.addClickListener(buttonClickEvent -> addProductToOrder(product));
            return addToOrderButton;
        });
        productGrid.setItems(availableProducts);
    }

    private void setupOrderProductGrid() {
        orderProductGrid.addColumn(orderProduct -> orderProduct.getProduct().getName()).setHeader("Product");
        orderProductGrid.addComponentColumn(orderProduct -> {
            String url = orderProduct.getProduct().getImageUrl();
            Image image = new Image(url != null? url : "" , orderProduct.getProduct().getName() + " image is missing");
            image.setWidth(50, Unit.PIXELS);
            image.setHeight(50, Unit.PIXELS);
            return image;
        }).setHeader("Image");
        orderProductGrid.addComponentColumn(orderProduct -> {
            Span quantity = new Span(orderProduct.getQuantity()+"");
            Div addButton = new Div();
            addButton.setText("+");
            addButton.setWidth(20, Unit.PIXELS);
            addButton.setHeight(20, Unit.PIXELS);
            addButton.addClickListener(buttonClickEvent -> increaseOrderProductQuantity(orderProduct, quantity));
            Div minusButton = new Div();
            minusButton.setText("-");
            minusButton.setWidth(20, Unit.PIXELS);
            minusButton.setHeight(20, Unit.PIXELS);
            minusButton.getStyle().set("font-weight", "bold");
            minusButton.addClickListener( buttonClickEvent -> decreaseOrderProductQuantity(orderProduct, quantity));
            HorizontalLayout addMinusLayout = new HorizontalLayout(minusButton, quantity, addButton);
            addMinusLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            return addMinusLayout;
        }).setHeader("Quantity");
        orderProductGrid.addColumn(orderProduct -> orderProduct.getPriceWithoutTax() + OrderForm.euroSymbol).setHeader("Price");

    }

    protected void addProductToOrder(Product product) {
        if (orderProducts.stream().noneMatch(orderProduct -> orderProduct.getProduct().equals(product))) {
            orderProducts.add(new OrderProduct(product));
            orderProductGrid.getDataProvider().refreshAll();
        }

    }

    private void increaseOrderProductQuantity(OrderProduct orderProduct, HasText hasText) {
        orderProduct.incrementQuantity();
        hasText.setText(orderProduct.getQuantity()+"");
        UpdateOrderProductGrid();
    }

    private void decreaseOrderProductQuantity(OrderProduct orderProduct, HasText hasText) {
        if (orderProduct.decrementQuantityOrRemove()) {
            orderProducts.remove(orderProduct);
        } else {
            hasText.setText(orderProduct.getQuantity()+"");
            // to update the price
        }
        UpdateOrderProductGrid();
    }

    private HorizontalLayout addToOrderField() {
        TextField productSearch = new TextField();
        productSearch.setPlaceholder("Filter by name or Barcode...");
        productSearch.setClearButtonVisible(true);
        productSearch.setValueChangeMode(ValueChangeMode.LAZY);

        Button addToOrderButton = new Button("Add to Order");
        addToOrderButton.addClickListener(click -> {});

        HorizontalLayout toolbar = new HorizontalLayout(productSearch, addToOrderButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void syncWithOrder(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
        UpdateOrderProductGrid();
    }

    private void UpdateOrderProductGrid() {
        orderProductGrid.setItems(orderProducts);
        productTotal.setText("Product total: " + orderProducts.stream().mapToDouble(OrderProduct::getPriceWithoutTax).sum() + OrderForm.euroSymbol);
        taxTotal.setText("Tax total: " + orderProducts.stream().mapToDouble(OrderProduct::getOnlyTaxPrice).sum() + OrderForm.euroSymbol);
        orderTotal.setText("Order total: " + orderProducts.stream().mapToDouble(OrderProduct::getPriceWithTax).sum() + OrderForm.euroSymbol);

    }
}
