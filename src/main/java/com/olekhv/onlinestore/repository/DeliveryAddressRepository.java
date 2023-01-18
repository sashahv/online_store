package com.olekhv.onlinestore.repository;

import com.olekhv.onlinestore.entity.order.DeliveryAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAddressRepository extends CrudRepository<DeliveryAddress, Long> {
}
