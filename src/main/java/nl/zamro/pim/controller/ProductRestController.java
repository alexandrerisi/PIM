package nl.zamro.pim.controller;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.domain.Product;
import nl.zamro.pim.service.CategoryService;
import nl.zamro.pim.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping("/rest/remove-product")
    public void removeProduct(@RequestParam(value = "id") String id) {
        Optional<Product> toBeRemoved = productService.getById(id);
        toBeRemoved.ifPresent(product -> productService.removeProduct(product));
    }

    @RequestMapping("/rest/save-product")
    public void saveProduct(@RequestBody Product param) {
        Optional<Category> category = categoryService.getById(param.getCategory().getId());
        if (category.isPresent()) {
            Optional<Product> dbProduct = productService.getById(param.getId());
            if (!dbProduct.isPresent()) {
                productService.saveProduct(param);
            } else {
                Product product = dbProduct.get();
                product.setName(param.getName());
                product.setDescription(param.getDescription());
                product.setMinOrderQuantity(param.getMinOrderQuantity());
                product.setUnitOfMeasure(param.getUnitOfMeasure());
                product.setCategory(param.getCategory());
                product.setPrice(param.getPrice());
                product.setAvailable(param.isAvailable());
                productService.saveProduct(product);
            }
        }
    }
}

//curl -i -X PUT -H 'Content-Type: application/json' -d '{"id":"123A","name":"Product Newton","description":"Desc","minOrderQuantity":"1","unitOfMeasure":"PR","category":{"id":"123","name":"ABC"},"price":"12.0","isAvailable":"true"}' http://localhost:8080/rest/save-product
