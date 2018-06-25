package nl.zamro.pim.controller;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.domain.Product;
import nl.zamro.pim.service.CategoryService;
import nl.zamro.pim.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Rest endpoints to manipulate Products.
 */
@RestController
public class ProductRestController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/rest/product")
    public Product getProduct(@RequestParam(value = "id") String id) {
        Optional<Product> product = productService.getById(id);
        return product.orElse(null);
    }

    @RequestMapping("/rest/all-products")
    public Iterable<Product> getAllCategories() {
        return productService.getAllProducts();
    }

    @RequestMapping("/rest/remove-product")
    public void removeCategory(@RequestParam(value = "id") String id) {
        Optional<Product> toBeRemoved = productService.getById(id);
        toBeRemoved.ifPresent(product -> productService.removeProduct(product));
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
        Optional<Category> category = categoryService.getById(categoryId);
        if (category.isPresent()) {
            Product product = new Product(
                    id, name, description, minOrder, measureUnit, category.get(), price, isAvailable);
            productService.saveProduct(product);
        }
    }
}
