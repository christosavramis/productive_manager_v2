package com.example.application.ui.views.forms;


import com.example.application.backend.data.entities.Policy;
import com.example.application.ui.crud.AbstractForm;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H1;

public class PolicyForm extends AbstractForm<Policy> {
  H1 name = new H1("name");
  private final ComboBox<String> selectedValue = new ComboBox<>("value");
  public PolicyForm() {
    super(Policy.class);
    add(name, selectedValue);
    addButtons();
  }

  @Override
  public void setFormObjectSync(Policy formObject) {
    super.setFormObjectSync(formObject);
    if (formObject!= null) {
      name.setText(formObject.getName());
      selectedValue.setItems(formObject.getValues());
      selectedValue.setValue(formObject.getUserSelectedValue());
      getBinder().forField(selectedValue).bind(Policy::getUserSelectedValue, Policy::setSelectedValue);
    }
  }

}
