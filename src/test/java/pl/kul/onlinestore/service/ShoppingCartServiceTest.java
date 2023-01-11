//package pl.kul.onlinestore.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import pl.kul.onlinestore.dto.OrderDTO;
//import pl.kul.onlinestore.entity.Category;
//import pl.kul.onlinestore.entity.Product;
//import pl.kul.onlinestore.entity.ShoppingCart;
//import pl.kul.onlinestore.entity.user.User;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
//
//@ExtendWith(SpringExtension.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = NONE)
//class ShoppingCartServiceTest {
//    private final TestEntityManager entityManager;
//
//    private final ShoppingCartService shoppingCartService;
//
//    @Autowired
//    ShoppingCartServiceTest(TestEntityManager entityManager, ShoppingCartService shoppingCartService) {
//        this.entityManager = entityManager;
//        this.shoppingCartService = shoppingCartService;
//    }
//
//    @Test
//    public void should_place_new_order(){
//        User user = entityManager.find(User.class, 15L);
//
//        Product productOne = entityManager.find(Product.class, 1L);
//
//        Product productTwo = entityManager.find(Product.class, 3L);
//
//        List<ShoppingCart> cartItems = new ArrayList<>();
//        cartItems.add(ShoppingCart.builder()
//                .productId(1L)
//                .productName(productOne.getName())
//                .quantity(3)
//                .amount(320)
//                .build());
//        cartItems.add(ShoppingCart.builder()
//                .productId(3L)
//                .productName(productTwo.getName())
//                .quantity(1)
//                .amount(200)
//                .build());
//
//        OrderDTO orderDTO = OrderDTO.builder()
//                .orderDescription("Some description")
//                .cartItems(cartItems)
//                        .userEmail(user.getEmail())
//                .build();
//
//        var order = shoppingCartService.placeOrder(orderDTO);
//
//        entityManager.persist(order);
//
//        assertThat(order.getOrderId()>0);
//    }
//}