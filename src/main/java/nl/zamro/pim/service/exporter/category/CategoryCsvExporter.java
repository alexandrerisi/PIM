package nl.zamro.pim.service.exporter.category;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.service.exporter.DataExporter;

import java.io.ByteArrayInputStream;
import java.util.Collection;

public class CategoryCsvExporter implements DataExporter {

    @Override
    public ByteArrayInputStream generateExport(Collection collection) {
        Collection<Category> categories = (Collection<Category>) collection;
        StringBuilder sb = new StringBuilder("CategoryID,NAME\n");
        for (Category c : categories)
            sb.append(c.getId()).append(",").append("\"").append(c.getName()).append("\"").append("\n");

        return new ByteArrayInputStream(sb.toString().getBytes());
    }
}
