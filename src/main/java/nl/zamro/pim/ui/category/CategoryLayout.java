package nl.zamro.pim.ui.category;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ButtonRenderer;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.service.exporter.DataExporter;
import nl.zamro.pim.ui.TableControl;

import java.util.Collection;
import java.util.Set;

class CategoryLayout extends VerticalLayout {

    CategoryLayout() {
        CategoryUserInterface ui = ((CategoryUserInterface) UI.getCurrent());
        Grid<Category> grid = new Grid<>(new ListDataProvider<>(ui.categoryService.getAllCategories()));
        grid.setCaption("Categories");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Category::getId).setCaption("ID");
        grid.addColumn(Category::getName).setCaption("NAME");
        grid.addColumn(category -> "EDIT", new ButtonRenderer<>(clickEvent -> {
            Category cat = clickEvent.getItem();
            CategoryWindow editWindow = new CategoryWindow(ui.categoryService, grid, cat);
            ui.addWindow(editWindow);
            editWindow.setValues(cat.getId() + "", cat.getName());
        }));
        TableControl control = new TableControl();
        control.setAddListener((Button.ClickListener) event -> {
            CategoryWindow window = new CategoryWindow(ui.categoryService, grid, null);
            window.setVisible(true);
            ui.addWindow(window);
        });
        control.setRemoveListener((Button.ClickListener) event -> {
            Set<Category> selectedItems = grid.getSelectedItems();
            ui.categoryService.removeCategories(selectedItems);
            Collection<Category> categoryCollection = ((ListDataProvider<Category>) grid.getDataProvider()).getItems();
            categoryCollection.removeAll(selectedItems);
            grid.setItems(categoryCollection);
        });
        control.setFormatSelectorListener((HasValue.ValueChangeListener<String>) event -> {
            control.getExportButton().setEnabled(true);
            control.setExportListener((Button.ClickListener) event1 -> {
                control.getExportButton().setCaption("Download Export");
                StreamResource streamResource = new StreamResource((StreamResource.StreamSource) () -> {
                    Collection<Category> selected = grid.getSelectedItems();
                    DataExporter dataExporter = DataExporter.getExporter(false, control.getFormat());
                    control.getFormatSelector().setValue(null);
                    control.getExportButton().setEnabled(false);
                    control.removeComponent(control.getExportButton());
                    Button newExport = new Button("Generate Report");
                    newExport.setEnabled(false);
                    control.setExportButton(newExport);
                    control.addComponent(newExport);
                    control.setComponentAlignment(newExport, Alignment.BOTTOM_CENTER);
                    return dataExporter.generateExport(selected);
                }, "export." + control.getFormat());
                control.setStreamResource(streamResource);
            });
        });
        addComponent(grid);
        addComponent(control);
    }
}
