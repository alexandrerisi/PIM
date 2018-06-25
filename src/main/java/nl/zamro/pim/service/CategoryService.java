package nl.zamro.pim.service;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.repository.CategoryRepository;
import nl.zamro.pim.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void removeCategories(Collection<Category> categories) {
        if (!categories.isEmpty()) {
            for (Category c : categories)
                productRepository.removeByCategory(c);
            categoryRepository.deleteAll(categories);
        }
    }

    @Transactional
    public void removeCategory(Category category) {
        productRepository.removeByCategory(category);
        categoryRepository.delete(category);
    }

    public void saveCategory(Category c) {
        categoryRepository.save(c);
    }

    public Collection<Category> getAllCategories() {
        Collection<Category> categories = new HashSet<>();
        categoryRepository.findAll().forEach(categories::add);
        return categories;
    }

    public Optional<Category> getById(int id) {
        return categoryRepository.findById(id);
    }

}
