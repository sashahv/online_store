//package pl.kul.onlinestore.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import pl.kul.onlinestore.entity.Category;
//import pl.kul.onlinestore.entity.Product;
//import pl.kul.onlinestore.exception.ProductNotFoundException;
//import pl.kul.onlinestore.repository.ProductRepository;
//
//import java.math.BigDecimal;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class ProductServiceTest {
//
//    @Autowired
//    private ProductService productService;
//
//    @MockBean
//    private ProductRepository productRepository;
//
//    @BeforeEach
//    void setUp() {
//        Category category = Category.builder()
//                .name("Buty")
//                .description("Jakieś buty")
//                .id(1L)
//                .build();
//
//        Product product = Product.builder()
//                .name("Adidas Boots")
//                .category(category)
//                .description("Some description")
//                .price(new BigDecimal("250.0"))
//                .id(1L)
//                .build();
//
//        Mockito.when(productRepository.findAllByCategoryId(1L)).thenReturn(Collections.singletonList(product));
//    }
//
//    @Test
//    void when_valid_product_name_then_product_should_be_found(){
//        String productName = "Adidas Boots";
//        Product product = productService.fetchProductByNameContains(productName);
//
//        assertEquals(productName, product.getName());
//    }
//}