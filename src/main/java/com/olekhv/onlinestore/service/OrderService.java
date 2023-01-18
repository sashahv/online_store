package com.olekhv.onlinestore.service;

import com.olekhv.onlinestore.dto.OrderDTO;
import com.olekhv.onlinestore.dto.ResponseOrderDTO;
import com.olekhv.onlinestore.entity.ShoppingCart;
import com.olekhv.onlinestore.entity.order.DeliveryAddress;
import com.olekhv.onlinestore.entity.order.Order;
import com.olekhv.onlinestore.entity.order.OrderStatus;
import com.olekhv.onlinestore.entity.user.User;
import com.olekhv.onlinestore.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.olekhv.onlinestore.exception.OrderNotFoundException;
import com.olekhv.onlinestore.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final DeliveryAddressService deliveryAddressService;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        ShoppingCartService shoppingCartService,
                        UserService userService,
                        DeliveryAddressService deliveryAddressService,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.deliveryAddressService = deliveryAddressService;
        this.productService = productService;
    }

    public Order fetchOrderById(Long orderId){
        return this.orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException(String.format("Zamowienie: %d nie istnieje", orderId))
        );
    }
    public ResponseOrderDTO placeOrder(OrderDTO orderDTO){
        log.info("RequestPayload " + orderDTO.toString());
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();

        ShoppingCart shoppingCart = shoppingCartService.fetchShoppingCartById(orderDTO.getShoppingCartId());
        log.info("Shopping cart was found successfully");

        float amount = shoppingCartService.countTotalAmountOfShoppingCart(shoppingCart.getCartItems());

        User user = userService.fetchUserByEmail(orderDTO.getUserEmail());
        log.info("User was found successfully");

        DeliveryAddress deliveryAddress = orderDTO.getDeliveryAddress();

        Order order = generateOrder(orderDTO, shoppingCart, user, deliveryAddress);
        log.info("Order processed successfully");

        deliveryAddress.setOrder(order);
        deliveryAddressService.createDeliveryAddress(deliveryAddress);
        order.setDeliveryAddressId(deliveryAddress.getId());

        order.setOrderStatus(OrderStatus.IN_PROCESS);

        productService.decreaseAvailableQuantityOfProduct(order);
        log.info("Available quantity was decreased");

        return getResponseOrderDTO(orderDTO, responseOrderDTO, amount, order);
    }

    private Order generateOrder(OrderDTO orderDTO, ShoppingCart shoppingCart, User user, DeliveryAddress deliveryAddress) {
        Order order = new Order();
        order.setOrderDescription(orderDTO.getOrderDescription());
        order.setUser(user);
        order.setShoppingCart(shoppingCart);
        order.setDeliveryAddressId(deliveryAddress.getId());
        order.setOrderStatus(order.getOrderStatus());
        order = saveOrder(order);
        return order;
    }

    private ResponseOrderDTO getResponseOrderDTO(OrderDTO orderDTO,
                                                 ResponseOrderDTO responseOrderDTO,
                                                 float amount,
                                                 Order order) {
        LocalDateTime dateTime = LocalDateTime.now();

        responseOrderDTO.setAmount(BigDecimal.valueOf(amount));
        responseOrderDTO.setDateTime(dateTime);
        responseOrderDTO.setInvoiceNumber(new Random().nextInt(10000));
        responseOrderDTO.setOrderId(order.getId());
        responseOrderDTO.setOrderDescription(orderDTO.getOrderDescription());

        log.info("ResponseOrderDTO generated successfully");
        return responseOrderDTO;
    }

    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }
}
