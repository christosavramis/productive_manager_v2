package com.example.application.ui.views.forms;

import com.example.application.backend.data.entity.OrderProduct;
import com.example.application.backend.data.entity.Product;
import com.example.application.backend.util.StringUtil;
import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.function.Supplier;

class ProductOrderMaker extends VerticalLayout {
    private final Grid<OrderProduct> orderProductGrid = new Grid<>();
    private List<OrderProduct> orderProducts;
    private final Grid<Product> productGrid = new Grid<>();
    private Div productTotal = new Div();
    private Div taxTotal = new Div();
    private Div orderTotal = new Div();
    private Supplier<List<Product>> productSupplier;
    private Button addToOrderButton;
    private Button productAddToOrderButton = new Button("+");
    private Div addButton = new Div();
    private Div minusButton = new Div();

    ProductOrderMaker(Supplier<List<Product>> productSupplier ) {
        this.productSupplier = productSupplier;
        setupProductGrid();
        setupOrderProductGrid();

        VerticalLayout orderingLayout = new VerticalLayout();
        orderingLayout.setSizeFull();
        orderingLayout.add(new H2("Ordered products"), addToOrderField(), productGrid, orderProductGrid, new VerticalLayout(productTotal, taxTotal, orderTotal));
        add(orderingLayout);
//        getStyle().set("background-color", "red");
    }

    private void setupProductGrid() {
        productGrid.addColumn(Product::getName).setHeader("Product Name");
        productGrid.addComponentColumn(product -> {
            String url = product.getImageUrl();
            Image image = new Image(url != null? url : "" , product.getName() + " image is missing");
            image.setWidth(50, Unit.PIXELS);
            image.setHeight(50, Unit.PIXELS);
            return image;
        }).setHeader("Image");
        productGrid.addComponentColumn(product -> {
            productAddToOrderButton.addClickListener(buttonClickEvent -> addProductToOrder(product));
            return productAddToOrderButton;
        });
        updateProductGrid();
    }

    private void setupOrderProductGrid() {
        orderProductGrid.addColumn(orderProduct -> orderProduct.getProduct().getName()).setHeader("Product").setWidth("140px");
        orderProductGrid.addComponentColumn(orderProduct -> {
            String url = orderProduct.getProduct().getImageUrl();
            Image image = new Image(url != null? url : "" , orderProduct.getProduct().getName() + " image is missing");
            image.setWidth(50, Unit.PIXELS);
            image.setHeight(50, Unit.PIXELS);
            return image;
        }).setHeader("Image").setWidth("60px");
        orderProductGrid.addComponentColumn(orderProduct -> {
            Span quantity = new Span(orderProduct.getQuantity()+"");
            addButton.setText("+");
            addButton.setWidth(20, Unit.PIXELS);
            addButton.setHeight(20, Unit.PIXELS);
            addButton.addClickListener(buttonClickEvent -> increaseOrderProductQuantity(orderProduct, quantity));
            minusButton.setText("-");
            minusButton.setWidth(20, Unit.PIXELS);
            minusButton.setHeight(20, Unit.PIXELS);
            minusButton.getStyle().set("font-weight", "bold");
            minusButton.addClickListener( buttonClickEvent -> decreaseOrderProductQuantity(orderProduct, quantity));
            HorizontalLayout addMinusLayout = new HorizontalLayout(minusButton, quantity, addButton);
            addMinusLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            return addMinusLayout;
        }).setHeader("Quantity");
        orderProductGrid.addColumn(orderProduct -> StringUtil.formatPrice(orderProduct.getPriceWithoutTax())).setHeader("Price");
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

        addToOrderButton = new Button("Add to Order");
        addToOrderButton.addClickListener(click -> {
            Product product = productSupplier.get().stream()
                    .filter(productStored -> productStored.getBarcode() != null
                            && productStored.getBarcode().equals(productSearch.getValue()))
                    .findAny().orElse(null);
            if (product != null)
                addProductToOrder(product);
            else
                Notification.show("product not found").addThemeVariants(NotificationVariant.LUMO_ERROR);
        });

        HorizontalLayout toolbar = new HorizontalLayout(productSearch, addToOrderButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void syncWithOrder(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
        UpdateOrderProductGrid();
        updateProductGrid();
    }

    public void disableCartButtons(boolean shouldDisable) {
        addToOrderButton.setEnabled(!shouldDisable);
        addButton.setEnabled(!shouldDisable);
        minusButton.setEnabled(!shouldDisable);
        productAddToOrderButton.setEnabled(!shouldDisable);
    }

    private void UpdateOrderProductGrid() {
        orderProductGrid.setItems(orderProducts);
        productTotal.setText("Product total: " + StringUtil.formatPrice(orderProducts.stream().mapToDouble(OrderProduct::getPriceWithoutTax).sum()));
        taxTotal.setText("Tax total: " + StringUtil.formatPrice(orderProducts.stream().mapToDouble(OrderProduct::getOnlyTaxPrice).sum()));
        orderTotal.setText("Order total: " +  StringUtil.formatPrice(orderProducts.stream().mapToDouble(OrderProduct::getPriceWithTax).sum()));

    }

    private void updateProductGrid() {
        productGrid.setItems(productSupplier.get());
    }
}
