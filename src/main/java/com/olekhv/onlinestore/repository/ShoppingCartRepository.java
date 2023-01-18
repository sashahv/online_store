package com.olekhv.onlinestore.repository;

import com.olekhv.onlinestore.entity.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
}
