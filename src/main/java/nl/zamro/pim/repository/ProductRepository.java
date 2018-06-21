package nl.zamro.pim.repository;

import nl.zamro.pim.domain.Category;
import nl.zamro.pim.domain.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, String> {

    void removeByCategory(Category category);
}
