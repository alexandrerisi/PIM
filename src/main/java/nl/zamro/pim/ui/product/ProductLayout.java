package nl.zamro.pim.ui.product;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;

import nl.zamro.pim.domain.Product;
import nl.zamro.pim.service.exporter.DataExporter;
import nl.zamro.pim.ui.TableControl;

import java.util.Collection;
import java.util.Set;

class ProductLayout extends VerticalLayout {

    ProductLayout() {
        ProductUserInterface ui = (ProductUserInterface) UI.getCurrent();
        TableControl control = new TableControl();
        Grid<Product> grid = new Grid<>(new ListDataProvider<>(ui.productService.getAllProducts()));
        grid.setCaption("Products");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Product::getId).setCaption("ID");
        grid.addColumn(Product::getName).setCaption("NAME");
        grid.addColumn(Product::getDescription, new HtmlRenderer()).setCaption("DESCRIPTION");
        grid.addColumn(Product::getMinOrderQuantity).setCaption("MIN ORDER");
        grid.addColumn(Product::getCategory).setCaption("CATEGORY");
        grid.addColumn(Product::getPrice).setCaption("PRICE");
        grid.addColumn(Product::isAvailable).setCaption("AVAILABLE");
        grid.addColumn(product -> "Edit", new ButtonRenderer<>(clickEvent -> {
            Product p = clickEvent.getItem();
            Window window = new ProductWindow(
                    ui.productService, ui.categoryService.getAllCategories(), grid, p, control);
            ui.addWindow(window);
            window.setVisible(true);
        }));
        control.setAddListener((Button.ClickListener) clickEvent -> {
            Window window = new ProductWindow(
                    ui.productService, ui.categoryService.getAllCategories(), grid, null, control);
            ui.addWindow(window);
            window.setVisible(true);
        });
        control.setRemoveListener((Button.ClickListener) clickEvent -> {
            Set<Product> selectedItems = grid.getSelectedItems();
            ui.productService.removeProducts(selectedItems);
            Collection<Product> products = ((ListDataProvider<Product>) grid.getDataProvider()).getItems();
            products.removeAll(selectedItems);
            grid.setItems(products);
            control.setTotal(products.size());
        });
        control.setFormatSelectorListener((HasValue.ValueChangeListener<String>) event -> {
            control.getExportButton().setEnabled(true);
            control.setExportListener((Button.ClickListener) event1 -> {
                control.getExportButton().setCaption("Download Export");
                StreamResource streamResource = new StreamResource((StreamResource.StreamSource) () -> {
                    Collection<Product> selected = grid.getSelectedItems();
                    DataExporter dataExporter = DataExporter.getExporter(true, control.getFormat());
                    control.getFormatSelector().setValue(null);
                    control.getExportButton().setEnabled(false);
                    control.removeComponent(control.getExportButton());
                    Button newExport = new Button("Generate Report");
                    newExport.setEnabled(false);
                    control.setExportButton(newExport);
                    control.addComponent(newExport);
                    control.setComponentAlignment(newExport, Alignment.BOTTOM_CENTER);
                    return dataExporter.generateExport(selected);
                }, "product_export." + control.getFormat());
                control.setStreamResource(streamResource);
            });
        });
        control.setTotal(((ListDataProvider<Product>) grid.getDataProvider()).getItems().size());
        addComponent(grid);
        addComponent(control);
    }
}
