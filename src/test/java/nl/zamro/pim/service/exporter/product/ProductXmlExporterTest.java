package nl.zamro.pim.service.exporter.product;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.domain.Product;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class ProductXmlExporterTest {

    @Test
    public void generateExport() throws IOException {
        String xml = "<Products><product>" +
                "<ZamroID>123A</ZamroID>" +
                "<Name>Product Name</Name>" +
                "<Description>Product Description</Description>" +
                "<MinOrderQuantity>1</MinOrderQuantity>" +
                "<UnitOfMeasure>PR</UnitOfMeasure>" +
                "<CategoryID>123</CategoryID>" +
                "<PurchasePrice>12.0</PurchasePrice>" +
                "<Available>true</Available>" +
                "</product></Products>";
        ByteArrayInputStream original = new ByteArrayInputStream(xml.getBytes());
        Product product = new Product(
                "123A", "Product Name", "Product Description", (short) 1, "PR", new Category(123, "ABC"), 12, true);
        Collection<Product> col = new ArrayList<>();
        col.add(product);
        ProductXmlExporter exporter = new ProductXmlExporter();
        byte[] originalBytes = new byte[100];
        byte[] colBytes = new byte[100];
        original.read(originalBytes);
        exporter.generateExport(col).read(colBytes);
        assertTrue(Arrays.equals(originalBytes, colBytes));
    }
}