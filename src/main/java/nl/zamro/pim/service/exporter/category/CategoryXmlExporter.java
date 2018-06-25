package nl.zamro.pim.service.exporter.category;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.service.exporter.DataExporter;

import java.io.ByteArrayInputStream;
import java.util.Collection;

public class CategoryXmlExporter implements DataExporter {

    @Override
    public ByteArrayInputStream generateExport(Collection collection) {
        Collection<Category> categories = (Collection<Category>) collection;
        StringBuilder sb = new StringBuilder("<Categories>");
        for (Category c : categories) {
            sb.append("<category>");
            sb.append("<CategoryID>").append(c.getId()).append("</CategoryID>");
            sb.append("<Name>").append(c.getName()).append("</Name>");
            sb.append("</category>");
        }
        sb.append("</Categories>");
        return new ByteArrayInputStream(sb.toString().getBytes());
    }
}
