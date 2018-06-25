package nl.zamro.pim.ui.product;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.*;
import nl.zamro.pim.domain.Category;
import nl.zamro.pim.domain.Product;
import nl.zamro.pim.service.ProductService;
import nl.zamro.pim.ui.TableControl;
import org.vaadin.ui.NumberField;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class ProductWindow extends Window {

    private VerticalLayout layout;
    private TextField id = new TextField("ID");
    private TextField name = new TextField("NAME");
    private TextField description = new TextField("Description");
    private NumberField minOrder = new NumberField("Minimum Order");
    private TextField measureUnit = new TextField("Unit Measure");
    private NativeSelect<Category> categorySelector = new NativeSelect<>("Category");
    private NumberField price = new NumberField("Price");
    private CheckBox isAvailable = new CheckBox("Is Available? ");
    private TableControl control;

    private ProductService service;
    private Grid<Product> grid;
    private Product toBeModified;

    ProductWindow(ProductService service, Collection<Category> categories, Grid<Product> grid, Product toBeModified,
                  TableControl control) {
        this.control = control;
        this.service = service;
        this.grid = grid;
        categorySelector.setDataProvider(new ListDataProvider<>(categories));
        categorySelector.setEmptySelectionAllowed(false);
        setCaption("Add/Modify Product");
        layout = new VerticalLayout();
        Button add = new Button("SAVE PRODUCT");
        layout.addComponents(id, name, description, minOrder, measureUnit, categorySelector, price, isAvailable, add);
        layout.setComponentAlignment(add, Alignment.BOTTOM_CENTER);
        add.addClickListener((Button.ClickListener) event -> saveCategory());
        center();
        setContent(layout);

        if (toBeModified != null) {
            this.toBeModified = toBeModified;
            loadProductInfo();
        }
    }

    private void saveCategory() {
        if (!isValidProduct())
            layout.addComponent(new Label("One or more fields don't match the rules."));
        else {
            Set<Product> col = new HashSet<>(((ListDataProvider<Product>) grid.getDataProvider()).getItems());
            Product newProduct = new Product(id.getValue(),
                    name.getValue(),
                    description.getValue(),
                    (short) Double.parseDouble(minOrder.getValue()),
                    measureUnit.getValue(),
                    categorySelector.getValue(),
                    Float.parseFloat(price.getValue()),
                    isAvailable.getValue());
            if (toBeModified != null)
                col.remove(toBeModified);
            service.saveProduct(newProduct);
            col.add(newProduct);
            grid.setItems(col);
            control.setTotal(col.size());
            close();
        }
    }

    private boolean isValidProduct() {
        if (!id.getValue().isEmpty()
                && !name.getValue().isEmpty()
                && !description.getValue().isEmpty()
                && !measureUnit.getValue().isEmpty()
                && categorySelector.getValue() != null) {

            try {
                Float.parseFloat(minOrder.getValue());
                Float.parseFloat(price.getValue());
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private void loadProductInfo() {
        id.setValue(toBeModified.getId());
        name.setValue(toBeModified.getName());
        description.setValue(toBeModified.getDescription());
        minOrder.setValue((double) toBeModified.getMinOrderQuantity());
        measureUnit.setValue(toBeModified.getUnitOfMeasure());
        categorySelector.setValue(toBeModified.getCategory());
        price.setValue((double) toBeModified.getPrice());
        isAvailable.setValue(toBeModified.isAvailable());
    }
}
