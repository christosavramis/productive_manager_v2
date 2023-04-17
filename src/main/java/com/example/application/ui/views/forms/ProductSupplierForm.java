package com.example.application.ui.views.forms;


import com.example.application.backend.data.entities.ProductSupplier;
import com.example.application.ui.crud.AbstractForm;
import com.vaadin.flow.component.textfield.TextField;

public class ProductSupplierForm extends AbstractForm<ProductSupplier> {
  TextField name = new TextField("name");
  TextField email = new TextField("email");
  TextField phone = new TextField("phone");

  public ProductSupplierForm() {
    super(ProductSupplier.class);
    getBinder().bindInstanceFields(this);
    name.setRequired(true);
    name.setErrorMessage("name must be filled in!");
    add(name, email, phone);
    addButtons();
  }

}
