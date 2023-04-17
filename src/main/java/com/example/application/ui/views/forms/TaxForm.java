package com.example.application.ui.views.forms;


import com.example.application.backend.data.entities.Tax;
import com.example.application.ui.crud.AbstractForm;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

public class TaxForm extends AbstractForm<Tax> {
  TextField name = new TextField("name");
  NumberField value = new NumberField("value");

  public TaxForm() {
    super(Tax.class);
    getBinder().bindInstanceFields(this);
    name.setRequired(true);
    name.setErrorMessage("name must be filled in!");
    value.setRequiredIndicatorVisible(true);
    value.setErrorMessage("tax must be filled in!");
    add(name, value);
    addButtons();
  }

}
