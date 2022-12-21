//package com.store.onlinestore.service;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.store.onlinestore.exception.BadRequestException;
//import com.store.onlinestore.repository.ProductRepository;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ContextConfiguration(locations = {"/application-context.xml"})
//@ExtendWith(SpringExtension.class)
//class ProductServiceTest {
//
//    @Test
//    void testShowAllProducts() {
//        ProductRepository productRepository = mock(ProductRepository.class);
//        when(productRepository.findAll()).thenReturn(new ArrayList<>());
//        assertThrows(BadRequestException.class,
//                () -> (new ProductService(productRepository)).showAllProducts());
//        verify(productRepository).findAll();
//    }
//}
//
