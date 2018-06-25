package nl.zamro.pim.ui.category;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.service.CategoryService;
import nl.zamro.pim.ui.TableControl;
import org.vaadin.ui.NumberField;

import java.util.HashSet;
import java.util.Set;

class CategoryWindow extends Window {

    private VerticalLayout layout;
    private NumberField id = new NumberField("ID");
    private TextField name = new TextField("NAME");
    private CategoryService service;
    private Grid<Category> grid;
    private Category toBeModified;
    private TableControl control;

    CategoryWindow(CategoryService service, Grid<Category> grid, Category toBeModified, TableControl control) {
        this.service = service;
        this.grid = grid;
        this.control = control;
        setCaption("Add/Modify Category");
        layout = new VerticalLayout();
        Button add = new Button("SAVE CATEGORY");
        layout.addComponents(id, name, add);
        layout.setComponentAlignment(add, Alignment.BOTTOM_CENTER);
        add.addClickListener((Button.ClickListener) event -> saveCategory());
        center();
        setContent(layout);

        if (toBeModified != null) {
            this.toBeModified = toBeModified;
            loadCategoryInfo();
        }
    }

    private void saveCategory() {
        if (id.getValue().matches("\\d+")) {
            if (name.getValue().trim().isEmpty())
                layout.addComponent(new Label("Name cannot be empty."));
            else {
                Set<Category> col = new HashSet<>(((ListDataProvider<Category>) grid.getDataProvider()).getItems());
                Category category = new Category(Integer.parseInt(id.getValue()), name.getValue());
                service.saveCategory(category);
                if (toBeModified != null)
                    col.remove(toBeModified);
                col.add(category);
                grid.setItems(col);
                control.setTotal(col.size());
                close();
            }
        } else
            layout.addComponent(new Label("Only Numbers are allowed for the ID."));
    }

    private void loadCategoryInfo() {
        this.id.setValue(toBeModified.getId() + "");
        this.name.setValue(toBeModified.getName());
    }
}
