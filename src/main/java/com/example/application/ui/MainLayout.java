package com.example.application.ui;

import com.example.application.security.SecurityService;
import com.example.application.ui.views.*;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

public class MainLayout extends AppLayout {

    private SecurityService securityService;
    public MainLayout(@Autowired SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        VerticalLayout profileWrapper = new VerticalLayout();

        Image profileImage = new Image("images/avatar.png", "alt");
        String employeeName = securityService.getAuthenticatedUser() != null ? securityService.getAuthenticatedUser().getName() : "none";

        Text text = new Text("Welcome, " + employeeName);
        profileWrapper.add(profileImage, text, new Button("Log out", e -> securityService.logout()));
        profileWrapper.setAlignItems(FlexComponent.Alignment.CENTER);
        addToDrawer(profileWrapper);
        setupAccordionContent();
    }

    private void createHeader() {
        H1 logo = new H1("Productive Manager");
        logo.getStyle().set("white-space", "nowrap");
        logo.addClassNames("text-l", "m-m");
        HorizontalLayout headerLogo = new HorizontalLayout();
        headerLogo.setWidth("100%");
        headerLogo.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, headerLogo);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private Component createDrawer(String viewName, VaadinIcon viewIcon, Class<? extends Component> viewClass) {
        Icon icon = viewIcon.create();
        RouterLink link = new RouterLink();
        HorizontalLayout horizontalLayout = new HorizontalLayout(icon, new Span(viewName));
        horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        link.add(temporaryCSSForLinks(horizontalLayout));
        link.setRoute(viewClass);
        link.setHighlightCondition(HighlightConditions.sameLocation());
        return new VerticalLayout(link);
    }

    private AccordionPanel AccordionPanelInventory() {
        Image image = new Image("images/inventory.png", "inventory");
        image.setHeight(50, Unit.PIXELS);
        image.setWidth(50, Unit.PIXELS);

        HorizontalLayout logoName = new HorizontalLayout(image, new Span("Inventory"));
        logoName.setAlignItems(FlexComponent.Alignment.CENTER);

        return new AccordionPanel(temporaryCSSForLinks(logoName), createContent(
                createDrawer("Products", VaadinIcon.PACKAGE, ProductView.class),
                createDrawer("Categories", VaadinIcon.CALC_BOOK, CategoryView.class),
                createDrawer("Taxes", VaadinIcon.ABACUS, TaxView.class),
                createDrawer("Suppliers", VaadinIcon.CUBE, ProductSupplierView.class))
        );
    }

    private AccordionPanel accordionPanelOrdering() {
        Image image = new Image("images/ordering.png", "Orders");
        image.setHeight(50, Unit.PIXELS);
        image.setWidth(50, Unit.PIXELS);

        HorizontalLayout logoName = new HorizontalLayout(image, new Span("Ordering"));
        logoName.setAlignItems(FlexComponent.Alignment.CENTER);

        return new AccordionPanel(temporaryCSSForLinks(logoName), createContent(
                createDrawer("Orders", VaadinIcon.CART, OrderView.class),
                createDrawer("Customers", VaadinIcon.USER, CustomerView.class)
        ));
    }

    private AccordionPanel accordionPanelReports() {
        Image image = new Image("images/reports.png", "Audits");
        image.setHeight(50, Unit.PIXELS);
        image.setWidth(50, Unit.PIXELS);

        HorizontalLayout logoName = new HorizontalLayout(image, new Span("Reports"));
        logoName.setAlignItems(FlexComponent.Alignment.CENTER);

        return new AccordionPanel(temporaryCSSForLinks(logoName), createContent(
                createDrawer("Audits", VaadinIcon.RECORDS, AuditView.class),
                createDrawer("Statistics", VaadinIcon.CHART, ReportProductView.class),
                createDrawer("Employees", VaadinIcon.USER, EmployeeView.class),
                createDrawer("Settings", VaadinIcon.OPTIONS, PolicyView.class)
        ));
    }

    private void setupAccordionContent() {
        Accordion accordion = new Accordion();
        accordion.getStyle().set("padding-left", "10px");
        accordion.add(AccordionPanelInventory());
        accordion.add(accordionPanelOrdering());
        if (securityService.isAdmin()) {
            accordion.add(accordionPanelReports());
        }
        addToDrawer(accordion);
    }

    private VerticalLayout createContent(Component ...components) {
        VerticalLayout content = new VerticalLayout();
        content.add(components);
        return content;
    }

    private Component temporaryCSSForLinks(HasStyle component) {
        component.getStyle().set("user-select", "none");
        component.getStyle().set("user-drag", "none");
        component.getStyle().set("cursor", "pointer");
        return (Component) component;
        //To be removed and refactored into a css class in the themes CSS
    }

}
