package com.example.application.ui.views.forms;

import com.example.application.backend.data.entities.ProductSupplier;
import com.example.application.backend.util.StringUtil;
import com.example.application.backend.data.entities.Category;
import com.example.application.backend.data.entities.Product;
import com.example.application.backend.data.entities.Tax;
import com.example.application.backend.service.ImageService;
import com.example.application.ui.crud.AbstractForm;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

import java.util.List;
import java.util.function.Supplier;

public class ProductForm extends AbstractForm<Product> {
  TextField name = new TextField("name");
  TextField barcode = new TextField("barcode");
  NumberField cost = new NumberField("price buy");
  NumberField price = new NumberField("price sell");
  TextField quantity = new TextField("quantity");
  TextField imageUrl = new TextField("image URL");
  ComboBox<Category> category = new ComboBox<>("category");
  ComboBox<Tax> tax = new ComboBox<>("tax");
  ComboBox<ProductSupplier> supplier = new ComboBox<>("supplier");
  Checkbox enabled = new Checkbox("enabled");
  private Upload upload;
  private ImageService imageService;

  public void setCategories(List<Category> categories) {
    category.setItems(categories);
  }

  public ProductForm(Supplier<List<Category>> categories, Supplier<List<Tax>> taxes, ImageService imageService, Supplier<List<ProductSupplier>> suppliers) {
    super(Product.class);
    this.imageService = imageService;

    getBinder().forField(quantity)
            .withConverter(new StringToIntegerConverter("Not a number"))
            .bind(Product::getQuantity, Product::setQuantity);
    getBinder().bindInstanceFields(this);
    name.setRequired(true);
    name.setErrorMessage("name must be filled in!");

    category.setItems(categories.get());
    category.setErrorMessage("category must be filled in!");
    category.setItemLabelGenerator(Category::getName);
    category.setRequired(true);

    tax.setItems(taxes.get());
    tax.setErrorMessage("tax must be filled in!");
    tax.setRequired(true);
    tax.setItemLabelGenerator(Tax::getName);

    supplier.setItems(suppliers.get());
    supplier.setErrorMessage("tax must be filled in!");
    supplier.setRequired(false);
    supplier.setItemLabelGenerator(ProductSupplier::getName);

    initUploaderImage();
    add(name, barcode, imageUrl, upload, category, tax, supplier, cost, price, quantity, enabled);
    addButtons();
  }


  @Override
  public boolean isValidForSave() {
    return !name.getValue().isEmpty()
            && tax.getValue() != null
            && category.getValue() != null;
  }

  private void initUploaderImage() {
    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    upload = new Upload(buffer);
    upload.setAcceptedFileTypes("image/jpeg","image/jpg", "image/png", "image/gif");
    upload.addSucceededListener(event -> imageUrl.setValue(
            imageService.saveImage(event.getFileName(),
                    StringUtil.FirstNotNullOrEmpty(name.getValue(), event.getFileName()),
                    buffer))
    );
    add(upload);
  }

  @Override
  public void validateAndSave() {
    super.validateAndSave();

  }

  @Override
  public void setFormObjectSync(Product formObject) {
    super.setFormObjectSync(formObject);
    upload.clearFileList();
  }
}
