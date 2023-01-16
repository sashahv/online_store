package pl.kul.onlinestore.repository;
import pl.kul.onlinestore.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findCategoryByName(String name);
}
