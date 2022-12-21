package com.store.onlinestore.repository;
import com.store.onlinestore.entity.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Category findCategoryByName(String name);
}
