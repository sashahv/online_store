package com.olekhv.onlinestore.service;

import com.olekhv.onlinestore.dto.CartItemDTO;
import com.olekhv.onlinestore.entity.CartItem;
import com.olekhv.onlinestore.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.olekhv.onlinestore.repository.CartItemRepository;

import java.util.List;

@Service
@Slf4j
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository, ProductService productService) {
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }

    public List<CartItem> fetchCartItemsByShoppingCartId(Long id){
        return cartItemRepository.findCartItemsByShoppingCartId(id);
    }

    public CartItem generateCartItem(CartItemDTO cartItemDTO){
        Product product = productService.fetchProductById(cartItemDTO.getProductId());
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItemDTO.getQuantity());
        return cartItem;
    }
}
