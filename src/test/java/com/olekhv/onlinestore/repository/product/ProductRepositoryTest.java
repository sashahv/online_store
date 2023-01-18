package com.olekhv.onlinestore.repository.product;

import com.olekhv.onlinestore.entity.Category;
import com.olekhv.onlinestore.entity.Product;
import com.olekhv.onlinestore.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp(){

    }

    @Test
    void should_check_if_product_exists_by_name(){
        Category category = entityManager.find(Category.class, 5L);
        Product product = Product.builder()
                .name("Adidas Boots")
                .description("Boots")
                .category(category)
                .availableQuantity(500)
                .price(BigDecimal.valueOf(500))
                .build();

       String name = entityManager.persist(product).getName();
       entityManager.clear();

       Optional<Product> product1 = productRepository.findProductByNameContains(name);

       assertTrue(product1.isPresent());
    }

    @Test
    void should_create_new_product_with_category(){
    }
}