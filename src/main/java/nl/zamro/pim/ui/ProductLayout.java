package nl.zamro.pim.ui;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Grid;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;

import nl.zamro.pim.domain.Product;

public class ProductLayout extends VerticalLayout {

    public ProductLayout() {
        UserInterface ui = (UserInterface) UI.getCurrent();
        TableControl productsControl = new TableControl();
        Grid<Product> productGrid = new Grid<>(new ListDataProvider<>(ui.productService.getAllProducts()));
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

        productsControl.setTotal(((ListDataProvider<Product>) productGrid.getDataProvider()).getItems().size());

        addComponent(productGrid);
        addComponent(productsControl);
    }
}
