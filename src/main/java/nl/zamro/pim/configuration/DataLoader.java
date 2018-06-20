package nl.zamro.pim.configuration;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.domain.Product;
import nl.zamro.pim.repository.CategoryRepository;
import nl.zamro.pim.repository.ProductRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Configuration
public class DataLoader {

    @Value("${categoryCsvPath}")
    private String categoryCsvPath;
    @Value("${productCsvPath}")
    private String productCsvPath;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    /**
     * Loads all categories into the Database.
     */
    @PostConstruct
    public void loadData() {
        try {
            Reader in = new InputStreamReader(new FileInputStream(categoryCsvPath), Charset.forName("ISO-8859-1"));
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in) ;
            // Makes sure the same category won't be added multiple times.
            Collection<Category> categories = new HashSet<>();
            for (CSVRecord record : records) {
                int id = Integer.parseInt(record.get("CategoryID").trim());
                String name = record.get("Name");
                categories.add(new Category(id, name));
            }
            // Save categories on the Database.
            categoryRepository.saveAll(categories);
            // Ensures that the products are always loaded after the categories.
            loadProducts();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads all products from the csv into the Database.
     */
    private void loadProducts() {
        try {
            Reader in = new InputStreamReader(new FileInputStream(productCsvPath), Charset.forName("ISO-8859-1"));
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            // Make sure the same product won't be added multiple times.
            Collection<Product> products = new HashSet<>();
            for (CSVRecord record : records) {
                String id = record.get("ZamroID");
                String name = record.get("Name");
                String description = record.get("Description");
                short minOrder;
                try {
                    minOrder = Short.parseShort(record.get("MinOrderQuantity").trim());
                } catch (NumberFormatException e) {
                    minOrder = 0;
                }
                String measureUnit = record.get("UnitOfMeasure");
                Optional<Category> category = categoryRepository.findById(Integer.parseInt(record.get("CategoryID")));
                float price = Float.parseFloat(record.get("PurchasePrice").trim());
                boolean isAvailable = record.get("Available").equals("1");
                products.add(new Product(id, name, description, minOrder, measureUnit,
                        category.orElse(new Category()), price, isAvailable));
            }
            // Save the products on the Database.
            productRepository.saveAll(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
