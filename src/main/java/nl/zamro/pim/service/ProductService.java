package nl.zamro.pim.service;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.domain.Product;
import nl.zamro.pim.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Collection<Product> getAllProducts() {
        Collection<Product> products = new HashSet<>();
        repository.findAll().forEach(products::add);
        return products;
    }

    @Transactional
    public void removeProducts(Collection<Product> products) {
        repository.deleteAll(products);
    }

    public void saveProduct(Product p) {
        repository.save(p);
    }

    @Transactional
    public void removeByCategory(Category c) {
        repository.removeByCategory(c);
    }

    public Optional<Product> getById(String id) {
        return repository.findById(id);
    }

    @Transactional
    public void removeProduct(Product p) {
        repository.delete(p);
    }
}
