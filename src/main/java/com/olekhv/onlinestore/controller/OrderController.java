package com.olekhv.onlinestore.controller;

import com.olekhv.onlinestore.entity.order.Order;
import com.olekhv.onlinestore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.olekhv.onlinestore.dto.OrderDTO;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable Long orderId){
        Order order = orderService.fetchOrderById(orderId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.placeOrder(orderDTO));
    }
}
