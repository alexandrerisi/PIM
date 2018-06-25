package nl.zamro.pim.controller;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.service.CategoryService;
import nl.zamro.pim.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Rest endpoints to manipulate Categories.
 */
@RestController
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/rest/category")
    public Category getCategory(@RequestParam(value = "id") int id) {
        Optional<Category> category = categoryService.getById(id);
        return category.orElse(null);
    }

    @RequestMapping("/rest/all-categories")
    public Iterable<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping("/rest/remove-category")
    public void removeCategory(@RequestParam(value = "id") int id) {
        Optional<Category> toBeRemoved = categoryService.getById(id);
        if (toBeRemoved.isPresent()) {
            productService.removeByCategory(toBeRemoved.get());
            categoryService.removeCategory(toBeRemoved.get());
        }
    }

    @RequestMapping("/rest/save-category")
    public void saveCategory(@RequestParam(value = "id") int id, @RequestParam(value = "name") String name) {
        Category category = new Category(id, name);
        categoryService.saveCategory(category);
    }
}
