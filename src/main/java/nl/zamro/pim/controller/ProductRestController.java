package nl.zamro.pim.controller;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.domain.Product;
import nl.zamro.pim.repository.CategoryRepository;
import nl.zamro.pim.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ProductRestController {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping("/rest/product")
    public Product getProduct(@RequestParam(value = "id") String id) {
        Optional<Product> product = repository.findById(id);
        return product.orElse(null);
    }

    @RequestMapping("/rest/all-products")
    public Iterable<Product> getAllCategories() {
        return repository.findAll();
    }

    @RequestMapping("/rest/remove-product")
    public void removeCategory(@RequestParam(value = "id") String id) {
        Optional<Product> toBeRemoved = repository.findById(id);
        toBeRemoved.ifPresent(product -> repository.delete(product));
    }

    @RequestMapping("/rest/save-product")
    public void saveCategory(@RequestParam(value = "id") String id,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "description") String description,
                             @RequestParam(value = "min_order") short minOrder,
                             @RequestParam(value = "measure_unit") String measureUnit,
                             @RequestParam(value = "category_id") int categoryId,
                             @RequestParam(value = "price") float price,
                             @RequestParam(value = "is_available") boolean isAvailable) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            Product product = new Product(
                    id, name, description, minOrder, measureUnit, category.get(), price, isAvailable);
            repository.save(product);
        }
    }
}
