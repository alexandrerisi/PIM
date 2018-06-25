package nl.zamro.pim.service.exporter.product;

import nl.zamro.pim.domain.Product;
import nl.zamro.pim.service.exporter.DataExporter;

import java.io.ByteArrayInputStream;
import java.util.Collection;

public class ProductCsvExporter implements DataExporter {

    @Override
    public ByteArrayInputStream generateExport(Collection collection) {
        Collection<Product> products = (Collection<Product>) collection;
        StringBuilder sb = new StringBuilder(
                "ZamroID,Name,Description,MinOrderQuantity,UnitOfMeasure,CategoryID,PurchasePrice,Available;\n");
        for (Product p : products) {
            sb.append(p.getId()).append(",")
                    .append("\"").append(p.getName()).append("\"").append(",")
                    .append("\"").append(p.getDescription()).append("\"").append(",")
                    .append(p.getMinOrderQuantity()).append(",")
                    .append("\"").append(p.getUnitOfMeasure()).append("\"").append(",")
                    .append(p.getCategory()).append(",")
                    .append(p.getPrice()).append(",")
                    .append(p.isAvailable()).append("\n");
        }
        return new ByteArrayInputStream(sb.toString().getBytes());
    }
}
