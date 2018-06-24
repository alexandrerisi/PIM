package nl.zamro.pim.ui;

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

import java.util.Collection;

class CategoryWindow extends Window {

    private VerticalLayout layout;
    private TextField id = new TextField("ID");
    private TextField name = new TextField("NAME");
    private CategoryService service;
    private Grid<Category> grid;

    CategoryWindow(CategoryService service, Grid<Category> grid) {

        this.service = service;
        this.grid = grid;
        setCaption("Add Category");
        layout = new VerticalLayout();
        Button add = new Button("ADD CATEGORY");
        layout.addComponents(id, name, add);
        layout.setComponentAlignment(add, Alignment.BOTTOM_CENTER);
        add.addClickListener((Button.ClickListener) event -> addCategory());
        center();
        setContent(layout);
    }

    private void addCategory() {
        if (id.getValue().matches("\\d+")) {
            if (name.getValue().trim().isEmpty())
                layout.addComponent(new Label("Name cannot be empty."));
            else {
                Category category = new Category(Integer.parseInt(id.getValue()), name.getValue());
                service.addCategory(category);
                Collection<Category> col = ((ListDataProvider<Category>) grid.getDataProvider()).getItems();
                col.add(category);
                grid.setItems(col);
                close();
            }
        } else
            layout.addComponent(new Label("Only Numbers are allowed for the ID."));
    }
}
