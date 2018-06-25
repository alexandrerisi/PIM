package nl.zamro.pim.controller;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.repository.CategoryRepository;
import nl.zamro.pim.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class CategoryRestController {

    @Autowired
    private CategoryRepository repository;
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/rest/category")
    public Category getCategory(@RequestParam(value = "id") int id) {
        Optional<Category> category = repository.findById(id);
        return category.orElse(null);
    }

    @RequestMapping("/rest/all-categories")
    public Iterable<Category> getAllCategories() {
        return repository.findAll();
    }

    @RequestMapping("/rest/remove-category")
    public void removeCategory(@RequestParam(value = "id") int id) {
        Optional<Category> toBeRemoved = repository.findById(id);
        if (toBeRemoved.isPresent()) {
            productRepository.removeByCategory(toBeRemoved.get());
            repository.delete(toBeRemoved.get());
        }
    }

    @RequestMapping("/rest/save-category")
    public void saveCategory(@RequestParam(value = "id") int id, @RequestParam(value = "name") String name) {
        Category category = new Category(id, name);
        repository.save(category);
    }
}
