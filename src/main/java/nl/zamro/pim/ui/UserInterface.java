package nl.zamro.pim.ui;

import com.sun.tools.javac.util.List;
import com.vaadin.annotations.Theme;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.domain.Product;
import nl.zamro.pim.service.CategoryService;
import nl.zamro.pim.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;

@Theme("valo")
@SpringUI
public class UserInterface extends UI {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    private VerticalLayout mainLayout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildUI();
        setContent(mainLayout);
    }

    private void buildUI() {
        Grid<Category> categoryGrid = new Grid<>(new ListDataProvider<>(categoryService.getAllCategories()));
        categoryGrid.setCaption("Categories");
        categoryGrid.setSizeFull();
        categoryGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        categoryGrid.addColumn(Category::getId).setCaption("ID");
        categoryGrid.addColumn(Category::getName).setCaption("NAME");
        categoryGrid.addColumn(category -> "Edit", new ButtonRenderer<>(clickEvent -> {
            // todo allow edit
        }));
        mainLayout.addComponent(categoryGrid);
        mainLayout.addComponent(new TableControl());

        Grid<Product> productGrid = new Grid<>(new ListDataProvider<>(productService.getAllProducts()));
        productGrid.setCaption("Products");
        productGrid.setSizeFull();
        productGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        productGrid.addColumn(Product::getId).setCaption("ID");
        productGrid.addColumn(Product::getName).setCaption("NAME");
        productGrid.addColumn(Product::getDescription, new HtmlRenderer()).setCaption("DESCRIPTION");
        productGrid.addColumn(Product::getMinOrderQuantity).setCaption("MIN ORDER");
        productGrid.addColumn(Product::getCategory).setCaption("CATEGORY");
        productGrid.addColumn(Product::getPrice).setCaption("PRICE");
        productGrid.addColumn(Product::isAvailable).setCaption("AVAILABLE");
        productGrid.addColumn(product -> "Edit", new ButtonRenderer<>(clickEvent -> {
            // todo allow edit
        }));
        mainLayout.addComponent(productGrid);
        mainLayout.addComponent(new TableControl());
    }
}

class TableControl extends HorizontalLayout {

    private Button add = new Button("Add");
    private Button remove = new Button("Remove");
    private Button export = new Button("Export");

    TableControl() {
        ComboBox<String> format = new ComboBox<>("Format");
        addComponents(add, remove, format, export);
        setComponentAlignment(add, Alignment.BOTTOM_CENTER);
        setComponentAlignment(remove, Alignment.BOTTOM_CENTER);
        setComponentAlignment(format, Alignment.BOTTOM_CENTER);
        setComponentAlignment(export, Alignment.BOTTOM_CENTER);
        format.setDataProvider(new ListDataProvider<String>(List.of("xml", "csv", "xls")));

        format.setEmptySelectionAllowed(false);
        format.setTextInputAllowed(false);
        export.setEnabled(false);
        format.addValueChangeListener((HasValue.ValueChangeListener<String>) event -> export.setEnabled(true));
    }

    void setAddListener(Button.ClickListener listener) {
        add.addClickListener(listener);
    }

    void setRemoveListener(Button.ClickListener listener) {
        remove.addClickListener(listener);
    }

    void setExportListener(Button.ClickListener listener) {
        export.addClickListener(listener);
    }
}
