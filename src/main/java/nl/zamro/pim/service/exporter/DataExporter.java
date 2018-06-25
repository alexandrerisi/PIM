package nl.zamro.pim.service.exporter;

import nl.zamro.pim.service.exporter.category.CategoryCsvExporter;
import nl.zamro.pim.service.exporter.category.CategoryJsonExporter;
import nl.zamro.pim.service.exporter.category.CategoryXmlExporter;
import nl.zamro.pim.service.exporter.product.ProductCsvExporter;
import nl.zamro.pim.service.exporter.product.ProductJsonExporter;
import nl.zamro.pim.service.exporter.product.ProductXmlExporter;

import java.io.ByteArrayInputStream;
import java.util.Collection;

public interface DataExporter {

    ByteArrayInputStream generateExport(Collection collection);

    static DataExporter getExporter(boolean isProduct, String format) {
        DataExporter exporter = null;
        if ("csv".equals(format))
            if (!isProduct)
                exporter = new CategoryCsvExporter();
            else
                exporter = new ProductCsvExporter();
        else if ("xml".equals(format))
            if (!isProduct)
                exporter = new CategoryXmlExporter();
            else
                exporter = new ProductXmlExporter();
        else if ("json".equals(format))
            if (!isProduct)
                exporter = new CategoryJsonExporter();
            else
                exporter = new ProductJsonExporter();

        return exporter;
    }
}
