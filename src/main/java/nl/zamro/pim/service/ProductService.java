package nl.zamro.pim.service;

import nl.zamro.pim.domain.Product;
import nl.zamro.pim.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;

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
}
