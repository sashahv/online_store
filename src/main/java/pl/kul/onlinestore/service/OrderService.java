package pl.kul.onlinestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kul.onlinestore.entity.Order;
import pl.kul.onlinestore.exception.OrderNotFoundException;
import pl.kul.onlinestore.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order fetchOrderById(Long orderId){
        return this.orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(String.format("Zamowienie: %d nie istnieje", orderId))
        );
    }

    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }
}
