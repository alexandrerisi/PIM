package nl.zamro.pim.ui.category;

import com.sun.tools.javac.util.List;
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

import java.util.HashSet;
import java.util.Set;

class CategoryWindow extends Window {

    private VerticalLayout layout;
    private TextField id = new TextField("ID");
    private TextField name = new TextField("NAME");
    private CategoryService service;
    private Grid<Category> grid;
    private Category toBeModified;

    CategoryWindow(CategoryService service, Grid<Category> grid, Category toBeModified) {
        this.toBeModified = toBeModified;
        this.service = service;
        this.grid = grid;
        setCaption("Add/Modify Category");
        layout = new VerticalLayout();
        Button add = new Button("SAVE CATEGORY");
        layout.addComponents(id, name, add);
        layout.setComponentAlignment(add, Alignment.BOTTOM_CENTER);
        add.addClickListener((Button.ClickListener) event -> saveCategory());
        center();
        setContent(layout);
    }

    private void saveCategory() {
        if (id.getValue().matches("\\d+")) {
            if (name.getValue().trim().isEmpty())
                layout.addComponent(new Label("Name cannot be empty."));
            else {
                Set<Category> col = new HashSet<>(((ListDataProvider<Category>) grid.getDataProvider()).getItems());
                Category category = new Category(Integer.parseInt(id.getValue()), name.getValue());

                if (toBeModified != null) {
                    service.removeCategories(List.of(toBeModified));
                    col.remove(toBeModified);
                }
                service.addCategory(category);
                col.add(category);
                grid.setItems(col);
                close();
            }
        } else
            layout.addComponent(new Label("Only Numbers are allowed for the ID."));
    }

    void setValues(String id, String name) {
        this.id.setValue(id);
        this.name.setValue(name);
    }
}
