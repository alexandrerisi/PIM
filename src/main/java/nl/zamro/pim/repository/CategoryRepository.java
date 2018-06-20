package nl.zamro.pim.repository;

import nl.zamro.pim.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}
