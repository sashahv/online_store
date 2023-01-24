package com.olekhv.onlinestore.service;

import com.olekhv.onlinestore.dto.CartItemDTO;
import com.olekhv.onlinestore.entity.CartItem;
import com.olekhv.onlinestore.entity.Product;
import com.olekhv.onlinestore.entity.ShoppingCart;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.olekhv.onlinestore.dto.ShoppingCartDTO;
import com.olekhv.onlinestore.exception.ShoppingCartNotFoundException;
import com.olekhv.onlinestore.repository.ShoppingCartRepository;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class ShoppingCartService {
    private final ProductService productService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;

    @Autowired
    public ShoppingCartService(ProductService productService,
                               ShoppingCartRepository shoppingCartRepository,
                               CartItemService cartItemService) {
        this.productService = productService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemService = cartItemService;
    }

    public ShoppingCart fetchShoppingCartById(Long id) {
        return shoppingCartRepository.findById(id)
                .orElseThrow(() -> new ShoppingCartNotFoundException(String.format("Kosz z indeksem [%d] nie został znaleziony", id)));
    }

    public ShoppingCart generateShoppingCart(ShoppingCartDTO shoppingCartDTO){
        ShoppingCart shoppingCart = new ShoppingCart();
        List<CartItem> cartItems =  new ArrayList<>();
        for (CartItemDTO cartItem : shoppingCartDTO.getCartItems()) {
            cartItems.add(cartItemService.generateCartItem(cartItem));
        }
        shoppingCart.setCartItems(cartItems);
        shoppingCart.setAmount(BigDecimal.valueOf(countTotalAmountOfShoppingCart(cartItems)));

        return shoppingCartRepository.save(shoppingCart);
    }

    public float countTotalAmountOfShoppingCart(List<CartItem> cartItems) {
        float totalShoppingCartAmount = 0f;

        for (CartItem cartItem : cartItems) {
            float cartItemAmount = countSingleCartItemAmount(cartItem);
            totalShoppingCartAmount += cartItemAmount;
        }

        return totalShoppingCartAmount;
    }

    private float countSingleCartItemAmount(CartItem cartItem) {
        float cartItemAmount=0f;
        
        Long productId = cartItem.getProduct().getId();
        Product product = productService.fetchProductById(productId);
        log.info("Product was found successfully / ProductId = {}", productId);
        float price = product.getPrice().floatValue();
        productService.addProduct(product);

        cartItemAmount = getCartItemAmount(cartItem, product, price);
        return cartItemAmount;
    }

    private float getCartItemAmount(CartItem cartItem, Product product, float price) {
        float cartItemAmount;
        if(product.getAvailableQuantity() < cartItem.getQuantity()) {
            cartItemAmount = price * product.getAvailableQuantity();
            cartItem.setQuantity(product.getAvailableQuantity());
        } else {
            cartItemAmount = cartItem.getQuantity() * price;
        }
        cartItem.setAmount(BigDecimal.valueOf(cartItemAmount));
        return cartItemAmount;
    }

    @Transactional
    public void deleteShoppingCart(Long id){
        shoppingCartRepository.findById(id).orElseThrow(
                () -> new ShoppingCartNotFoundException("Nie znaleziono kosza z id:" + id)
        );
        shoppingCartRepository.deleteById(id);
    }
}
