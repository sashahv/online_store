//package pl.kul.onlinestore.repository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import pl.kul.onlinestore.entity.*;
//
//import java.util.List;
//
//
//@SpringBootTest
//class ShoppingCartRepositoryTest {
//
//    @Autowired
//    ShoppingCartRepository shoppingCartRepository;
//
////    @Test
////    void should_add_cart_item_to_shopping_cart(){
////        Product product = Product.builder()
////                .name("Boots")
////                .description("Boots")
////                .category(Category.builder().name("CategoryOne").build())
////                .build();
////
////        CartItemOLD cartItemOLD = CartItemOLD.builder()
////                .product(product)
////                .quantity(2).build();
////
////        List<CartItemOLD> cartItemOLDS = List.of(cartItemOLD);
////
////        ShoppingCart shoppingCartBuilder = ShoppingCart.builder()
////                .cartItemOLDS(cartItemOLDS)
////                .totalAmount(123)
////                .description("Some text")
////                .build();
////
////        shoppingCartRepository.save(shoppingCartBuilder);
////    }
//
////    @Test
////    void should_create_new_shopping_cart() {
////        Product productTwo = Product.builder()
////                .name("T-Shirt")
////                .description("T-Shirt")
////                .category(Category.builder().name("CategoryTwo").build())
////                .build();
////
////
////
////        CartItem cartItemTwo = CartItem.builder()
////                .product(productTwo)
////                .quantity(2).build();
////
////        List<CartItem> cartItems = List.of(cartItem, cartItemTwo);
////
////        ShoppingCart shoppingCart = ShoppingCart.builder()
////                .cartItem(cartItems)
////                .build();
////
////        shoppingCartRepository.save(shoppingCart);
////    }
//}