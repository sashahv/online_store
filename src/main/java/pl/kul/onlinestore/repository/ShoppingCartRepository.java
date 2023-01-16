package pl.kul.onlinestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kul.onlinestore.entity.CartItem;
import pl.kul.onlinestore.entity.ShoppingCart;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
}
