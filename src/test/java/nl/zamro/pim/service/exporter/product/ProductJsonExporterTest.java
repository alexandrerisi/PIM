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

public class ProductJsonExporterTest {

    @Test
    public void generateExport() throws IOException {
        String json = "{ products : [ { ZamroID : \"123A\",Name : \"Product Name\"," +
                "Description : \"Product Description\",MinOrderQuantity : 1,UnitOfMeasure : \"PR\", " +
                "CategoryID : 123,PurchasePrice : 12,Available : true } ] }";
        ByteArrayInputStream original = new ByteArrayInputStream(json.getBytes());
        Product product = new Product("123A", "Product Name", "Product Description", (short) 1, "PR",
                new Category(123, "ABC"), 12, true);
        Collection<Product> col = new ArrayList<>();
        col.add(product);
        ProductJsonExporter exporter = new ProductJsonExporter();
        byte[] originalBytes = new byte[100];
        byte[] colBytes = new byte[100];
        original.read(originalBytes);
        exporter.generateExport(col).read(colBytes);
        assertTrue(Arrays.equals(originalBytes, colBytes));
    }
}