package nl.zamro.pim.service;

import nl.zamro.pim.domain.Product;
import nl.zamro.pim.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Collection<Product> getAllProducts() {
        Collection<Product> products = new HashSet<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }
}
