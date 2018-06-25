package nl.zamro.pim.service.exporter.product;

import nl.zamro.pim.domain.Product;
import nl.zamro.pim.service.exporter.DataExporter;

import java.io.ByteArrayInputStream;
import java.util.Collection;

public class ProductXmlExporter implements DataExporter {

    @Override
    public ByteArrayInputStream generateExport(Collection collection) {
        Collection<Product> products = (Collection<Product>) collection;
        StringBuilder sb = new StringBuilder("<Products>");
        for (Product p : products) {
            sb.append("<product>");
            sb.append("<ZamroID>").append(p.getId()).append("<ZamroID>");
            sb.append("<Name>").append(p.getName()).append("</Name>");
            sb.append("<Description>").append(p.getDescription()).append("</Description>");
            sb.append("<MinOrderQuantity>").append(p.getMinOrderQuantity()).append("</MinOrderQuantity>");
            sb.append("<UnitOfMeasure>").append(p.getUnitOfMeasure()).append("</UnitOfMeasure>");
            sb.append("<CategoryID>").append(p.getCategory()).append("</CategoryID>");
            sb.append("<PurchasePrice>").append(p.getPrice()).append("</PurchasePrice>");
            sb.append("<Available>").append(p.isAvailable()).append("</Available>");
            sb.append("</product>");
        }
        sb.append("</Products>");
        return new ByteArrayInputStream(sb.toString().getBytes());
    }
}
