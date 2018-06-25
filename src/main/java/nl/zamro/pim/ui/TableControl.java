package nl.zamro.pim.ui;

import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import java.util.Arrays;

/**
 * Creates the components used to manipulate the table.
 */
public class TableControl extends HorizontalLayout {

    private Button add = new Button("Add");
    private Button remove = new Button("Remove");
    private Button export = new Button("Generate Export");
    private ComboBox<String> format;
    private Label total = new Label();

    public TableControl() {
        format = new ComboBox<>("Format");
        export.setId("Export Button");
        addComponents(add, remove, format, export, total);
        setComponentAlignment(add, Alignment.BOTTOM_CENTER);
        setComponentAlignment(remove, Alignment.BOTTOM_CENTER);
        setComponentAlignment(format, Alignment.BOTTOM_CENTER);
        setComponentAlignment(export, Alignment.BOTTOM_CENTER);
        setComponentAlignment(total, Alignment.BOTTOM_CENTER);
        String[] options = { "xml", "csv", "json" };
        format.setDataProvider(new ListDataProvider<String>(Arrays.asList(options)));

        format.setEmptySelectionAllowed(false);
        format.setTextInputAllowed(false);
        export.setEnabled(false);
    }

    public void setAddListener(Button.ClickListener listener) {
        add.addClickListener(listener);
    }

    public void setRemoveListener(Button.ClickListener listener) {
        remove.addClickListener(listener);
    }

    public void setExportListener(Button.ClickListener listener) {
        export.addClickListener(listener);
    }

    public void setStreamResource(StreamResource resource) {
        FileDownloader downloader = new FileDownloader(resource);
        downloader.extend(export);
    }

    public void setFormatSelectorListener(HasValue.ValueChangeListener<String> listener) {
        format.addValueChangeListener(listener);
    }

    public Button getExportButton() {
        return export;
    }

    public void setExportButton(Button b) {
        export = b;
    }

    public ComboBox<String> getFormatSelector() {
        return format;
    }

    public String getFormat() {
        return format.getValue();
    }

    public void setTotal(int i) {
        total.setValue("Total: " + i);
    }
}
