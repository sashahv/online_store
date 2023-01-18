package com.olekhv.onlinestore.repository;

import com.olekhv.onlinestore.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAllByCategoryId(Long id);
    Optional<Product> findProductByNameContains(String name);
    void deleteAllByCategoryId(Long id);
}
