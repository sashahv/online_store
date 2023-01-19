package com.olekhv.onlinestore.service;

import com.olekhv.onlinestore.entity.CartItem;
import com.olekhv.onlinestore.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Test
    public void should_check_if_total_amount_of_shopping_cart_is_bigger_than_0(){
        Product product = Product.builder()
                .id(3L)
                .build();

        CartItem cartItem = CartItem.builder()
                .product(product)
                .quantity(3)
                .build();

        float totalAmountOfShoppingCart = shoppingCartService.countTotalAmountOfShoppingCart(Collections.singletonList(cartItem));

        assertTrue(totalAmountOfShoppingCart>0);
    }
}