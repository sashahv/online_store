package pl.kul.onlinestore.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.kul.onlinestore.entity.order.DeliveryAddress;

@Repository
public interface DeliveryAddressRepository extends CrudRepository<DeliveryAddress, Long> {
}
