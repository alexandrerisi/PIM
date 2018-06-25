package nl.zamro.pim.service.exporter.category;

import nl.zamro.pim.domain.Category;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class CategoryXmlExporterTest {

    @Test
    public void generateExport() throws IOException {
        String xml = "<Categories><category><CategoryID>123</CategoryID><Name>ABC</Name></category></Categories>";
        ByteArrayInputStream original = new ByteArrayInputStream(xml.getBytes());
        Category category = new Category(123, "ABC");
        Collection<Category> col = new ArrayList<>();
        col.add(category);
        CategoryXmlExporter exporter = new CategoryXmlExporter();
        byte[] originalBytes = new byte[100];
        byte[] colBytes = new byte[100];
        original.read(originalBytes);
        exporter.generateExport(col).read(colBytes);
        assertTrue(Arrays.equals(originalBytes, colBytes));
    }
}