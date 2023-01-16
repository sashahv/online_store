package pl.kul.onlinestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kul.onlinestore.entity.order.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
