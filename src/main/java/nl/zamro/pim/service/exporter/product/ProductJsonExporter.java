package nl.zamro.pim.service.exporter.product;

import nl.zamro.pim.domain.Product;
import nl.zamro.pim.service.exporter.DataExporter;

import java.io.ByteArrayInputStream;
import java.util.Collection;

public class ProductJsonExporter implements DataExporter {

    @Override
    public ByteArrayInputStream generateExport(Collection collection) {
        Collection<Product> products = (Collection<Product>) collection;
        StringBuilder sb = new StringBuilder("{ products : [ ");
        for (Product p : products) {
            sb.append("{ ZamroID : \"").append(p.getId()).append("\",");
            sb.append("Name : \"").append(p.getName()).append("\",");
            sb.append("Description : \"").append(p.getDescription()).append("\",");
            sb.append("MinOrderQuantity : ").append(p.getMinOrderQuantity()).append(",");
            sb.append("UnitOfMeasure : \"").append(p.getUnitOfMeasure()).append("\",");
            sb.append("CategoryID : ").append(p.getCategory()).append(",");
            sb.append("PurchasePrice : ").append(p.getPrice()).append(",");
            sb.append("Available : ").append(p.isAvailable()).append(" },");
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append(" ] }");
        return new ByteArrayInputStream(sb.toString().getBytes());
    }
}
