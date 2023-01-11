package pl.kul.onlinestore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kul.onlinestore.dto.OrderDTO;
import pl.kul.onlinestore.dto.ResponseOrderDTO;
import pl.kul.onlinestore.entity.Order;
import pl.kul.onlinestore.entity.Product;
import pl.kul.onlinestore.entity.ShoppingCart;
import pl.kul.onlinestore.entity.user.User;
import pl.kul.onlinestore.exception.ShoppingCartNotFound;
import pl.kul.onlinestore.repository.ShoppingCartRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class ShoppingCartService {
    private final ProductService productService;
    private final OrderService orderService;
    private final UserService userService;
    private final ShoppingCartRepository shoppingCartRepository;

    @Autowired
    public ShoppingCartService(ProductService productService, OrderService orderService, UserService userService, ShoppingCartRepository shoppingCartRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.userService = userService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public ShoppingCart getShoppingCart(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ShoppingCartNotFound(String.format("Kosz z indeksem [%d] nie został znaleziony", id)));
    }

    public Order getOrderDetailsById(Long orderId){
        return orderService.fetchOrderById(orderId);
    }

    public ResponseOrderDTO placeOrder(OrderDTO orderDTO){
        log.info("RequestPayload " + orderDTO.toString());
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        float amount = getCartAmount(orderDTO.getCartItems());

        User user = userService.fetchUserByEmail(orderDTO.getUserEmail());
        log.info("User was found successfully");

        Order order = new Order(orderDTO.getOrderDescription(), user, orderDTO.getCartItems());
        order = saveOrder(order);
        log.info("Order processed successfully..");

        LocalDateTime dateTime = LocalDateTime.now();

        responseOrderDTO.setAmount(BigDecimal.valueOf(amount));
        responseOrderDTO.setDateTime(dateTime);
        responseOrderDTO.setInvoiceNumber(new Random().nextInt(10000));
        responseOrderDTO.setOrderId(order.getId());
        responseOrderDTO.setOrderDescription(orderDTO.getOrderDescription());

        log.info("ResponseOrderDTO generated successfully");
        return responseOrderDTO;
    }

    private Order saveOrder(Order order) {
        return orderService.saveOrder(order);
    }

    private float getCartAmount(List<ShoppingCart> cartItems) {
        float totalCartAmount = 0f;
        float singleCartAmount = 0f;
        int availableQuantity = 0;


        for (ShoppingCart cartItem : cartItems) {
            Long productId = cartItem.getProductId();
            Product product = productService.fetchProductById(productId);
            log.info("Product was found successfully");

            BigDecimal price = new BigDecimal(String.valueOf(product.getPrice()));
            float convertedPrice = price.floatValue();
            if(product.getAvailableQuantity() < cartItem.getQuantity()) {
                singleCartAmount = convertedPrice * product.getAvailableQuantity();
                cartItem.setQuantity(product.getAvailableQuantity());
            } else {
                singleCartAmount = cartItem.getQuantity() * convertedPrice;
                availableQuantity = product.getAvailableQuantity() - cartItem.getQuantity();
            }
            totalCartAmount = totalCartAmount + singleCartAmount;
            log.info("Total cart amount was counted successfully");
            product.setAvailableQuantity(availableQuantity);
            availableQuantity=0;
            cartItem.setProductName(product.getName());
            cartItem.setAmount(singleCartAmount);
            log.info("Cart item was created successfully");
            productService.addProduct(product);
        }
        return totalCartAmount;
    }
}
