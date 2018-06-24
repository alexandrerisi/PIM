package nl.zamro.pim.ui;

import com.sun.tools.javac.util.List;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

class TableControl extends HorizontalLayout {

    private Button add = new Button("Add");
    private Button remove = new Button("Remove");
    private StreamResource streamResource;
    private FileDownloader downloader;
    private Button export = new Button("Generate Export");
    private ComboBox<String> format;
    private Label total = new Label();

    TableControl() {
        format = new ComboBox<>("Format");
        export.setId("Export Button");
        addComponents(add, remove, format, export, total);
        setComponentAlignment(add, Alignment.BOTTOM_CENTER);
        setComponentAlignment(remove, Alignment.BOTTOM_CENTER);
        setComponentAlignment(format, Alignment.BOTTOM_CENTER);
        setComponentAlignment(export, Alignment.BOTTOM_CENTER);
        setComponentAlignment(total, Alignment.BOTTOM_CENTER);
        format.setDataProvider(new ListDataProvider<String>(List.of("xml", "csv", "json")));

        format.setEmptySelectionAllowed(false);
        format.setTextInputAllowed(false);
        export.setEnabled(false);
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

    void setStreamResource(StreamResource resource) {
        streamResource = resource;
        downloader = new FileDownloader(streamResource);
        downloader.extend(export);
    }

    void removeStreamResource() {
        downloader = null;
        streamResource = null;
    }

    void setFormatSelectorListener(HasValue.ValueChangeListener<String> listener) {
        format.addValueChangeListener(listener);
    }

    Button getExportButton() {
        return export;
    }

    void setExportButton(Button b) {
        export = b;
    }

    ComboBox<String> getFormatSelector() {
        return format;
    }

    String getFormat() {
        return format.getValue();
    }

    void setTotal(int i) {
        total.setValue("Total: " + i);
    }
}
