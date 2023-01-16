package pl.kul.onlinestore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kul.onlinestore.dto.OrderDTO;
import pl.kul.onlinestore.dto.ResponseOrderDTO;
import pl.kul.onlinestore.dto.ShoppingCartDTO;
import pl.kul.onlinestore.entity.*;
import pl.kul.onlinestore.entity.order.DeliveryAddress;
import pl.kul.onlinestore.entity.order.Order;
import pl.kul.onlinestore.entity.order.OrderStatus;
import pl.kul.onlinestore.entity.user.User;
import pl.kul.onlinestore.exception.ShoppingCartNotFoundException;
import pl.kul.onlinestore.repository.ShoppingCartRepository;
import pl.kul.onlinestore.service.user.UserService;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class ShoppingCartService {
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final DeliveryAddressService deliveryAddressService;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartService(ProductService productService,
                               OrderService orderService,
                               UserService userService,
                               DeliveryAddressService deliveryAddressService,
                               ShoppingCartRepository shoppingCartRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
        this.deliveryAddressService = deliveryAddressService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCart fetchShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ShoppingCartNotFoundException(String.format("Kosz z indeksem [%d] nie został znaleziony", id)));
    }

    public Order fetchOrderById(Long orderId){
        return orderService.fetchOrderById(orderId);
    }

    public ShoppingCart generateShoppingCart(ShoppingCartDTO shoppingCartDTO){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartItems(shoppingCartDTO.getCartItems());
        shoppingCart.setAmount(BigDecimal.valueOf(getCartAmount(shoppingCartDTO.getCartItems())));

        return shoppingCartRepository.save(shoppingCart);
    }

    public ResponseOrderDTO placeOrder(OrderDTO orderDTO){
        log.info("RequestPayload " + orderDTO.toString());
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();

        ShoppingCart shoppingCart = fetchShoppingCartById(orderDTO.getShoppingCartId());
        log.info("Shopping cart was found successfully");

        float amount = getCartAmount(shoppingCart.getCartItems());

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

    private float getCartAmount(List<CartItem> cartItems) {
        float totalCartAmount = 0f;


        for (CartItem cartItem : cartItems) {
            Long productId = cartItem.getProductId();
            log.info("ProductId = {}", productId);
            Product product = productService.fetchProductById(productId);
            log.info("Product was found successfully");

            BigDecimal price = new BigDecimal(String.valueOf(product.getPrice()));
            float convertedPrice = price.floatValue();
            float singleCartAmount = getSingleCartAmount(cartItem, product, convertedPrice);
            totalCartAmount = totalCartAmount + singleCartAmount;
            log.info("Total cart amount was counted successfully");
            cartItem.setAmount(BigDecimal.valueOf(singleCartAmount));
            log.info("Cart item was created successfully");
            productService.addProduct(product);
        }
        return totalCartAmount;
    }

    private float getSingleCartAmount(CartItem cartItem, Product product, float convertedPrice) {
        float singleCartAmount=0f;
        if(product.getAvailableQuantity() < cartItem.getQuantity()) {
            singleCartAmount = convertedPrice * product.getAvailableQuantity();
            cartItem.setQuantity(product.getAvailableQuantity());
        } else {
            singleCartAmount = cartItem.getQuantity() * convertedPrice;
        }
        return singleCartAmount;
    }

    @Transactional
    public void deleteShoppingCart(Long id){
        shoppingCartRepository.findById(id).orElseThrow(
                () -> new ShoppingCartNotFoundException("Nie znaleziono kosza z id:" + id)
        );
        shoppingCartRepository.deleteById(id);
    }

    private Order saveOrder(Order order) {
        return orderService.saveOrder(order);
    }
}
