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

public class ProductCsvExporterTest {

    @Test
    public void generateExport() throws IOException {
        String csv = "ZamroID,Name,Description,MinOrderQuantity,UnitOfMeasure,CategoryID,PurchasePrice,Available" +
                "\nA123,\"Product Name\",\"Product Description\",1,\"PR\",123,12,true\n";
        ByteArrayInputStream original = new ByteArrayInputStream(csv.getBytes());
        Product product = new Product("A123", "Product Name", "Product Description", (short) 1, "PR",
                new Category(123, "Category"), 12, true);
        Collection<Product> col = new ArrayList<>();
        col.add(product);
        ProductCsvExporter exporter = new ProductCsvExporter();
        byte[] originalBytes = new byte[100];
        byte[] colBytes = new byte[100];
        original.read(originalBytes);
        exporter.generateExport(col).read(colBytes);
        assertTrue(Arrays.equals(originalBytes, colBytes));
    }
}