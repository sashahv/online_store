package pl.kul.onlinestore.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kul.onlinestore.entity.CartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
    @Query(value = "SELECT * FROM cart_item WHERE cart_id=?1", nativeQuery = true)
    List<CartItem> findCartItemsByShoppingCartId(Long id);
}
