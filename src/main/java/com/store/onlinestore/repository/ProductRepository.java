package com.store.onlinestore.repository;

import com.store.onlinestore.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAllByCategoryId(Long id);
    void deleteAllByCategoryId(Long id);
}
