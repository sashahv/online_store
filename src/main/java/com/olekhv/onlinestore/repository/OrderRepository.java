package com.olekhv.onlinestore.repository;

import com.olekhv.onlinestore.entity.order.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
