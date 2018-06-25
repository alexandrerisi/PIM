package nl.zamro.pim.service.exporter.category;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.service.exporter.DataExporter;

import java.io.ByteArrayInputStream;
import java.util.Collection;

public class CategoryJsonExporter implements DataExporter {

    @Override
    public ByteArrayInputStream generateExport(Collection collection) {
        Collection<Category> categories = (Collection<Category>) collection;
        StringBuilder sb = new StringBuilder("{ categories: [ ");
        for (Category c : categories) {
            sb.append(" { CategoryID : \"").append(c.getId()).append("\",").
                    append(" Name: ").append("\"").append(c.getName()).append("\" },");
        }
        sb.delete(sb.length() - 1, sb.length());
        sb.append(" ] }");
        return new ByteArrayInputStream(sb.toString().getBytes());
    }
}
