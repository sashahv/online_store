package com.olekhv.onlinestore.repository;

import com.olekhv.onlinestore.entity.CartItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    @Query(value = "SELECT * FROM cart_item WHERE cart_id=?1", nativeQuery = true)
    List<CartItem> findCartItemsByShoppingCartId(Long id);
}
