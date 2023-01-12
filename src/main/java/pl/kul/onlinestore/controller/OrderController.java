package pl.kul.onlinestore.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kul.onlinestore.dto.OrderDTO;
import pl.kul.onlinestore.entity.order.Order;
import pl.kul.onlinestore.service.OrderService;
import pl.kul.onlinestore.service.ShoppingCartService;

@RestController
@Log4j2
@RequestMapping("api/v1/orders")
public class OrderController {

    private final ShoppingCartService shoppingCartService;
    private final OrderService orderService;

    @Autowired
    public OrderController(ShoppingCartService shoppingCartService, OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId){
        Order order = orderService.fetchOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(shoppingCartService.placeOrder(orderDTO));
    }
}
