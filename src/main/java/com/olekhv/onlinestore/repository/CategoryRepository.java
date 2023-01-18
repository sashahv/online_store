package com.olekhv.onlinestore.repository;
import com.olekhv.onlinestore.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findCategoryByName(String name);
}
