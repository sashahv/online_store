package com.store.onlinestore.service;

import com.store.onlinestore.entity.Product;
import com.store.onlinestore.exception.BadRequestException;
import com.store.onlinestore.exception.ProductNotFoundException;
import com.store.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> showAllProducts() throws ProductNotFoundException {
        List<Product> products = new ArrayList<>();
        productRepository
                .findAll()
                .forEach(products::add);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("There are no products yet");
        }
        return products;
    }

    public List<Product> showProductsByCategory(Long categoryId){
        return productRepository.findAllByCategoryId(categoryId);
    }

    public Optional<Product> getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ProductNotFoundException(String.format("Produkt z indeksem [%d] nie był znaleziony", id));
        }
        return product;
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(@PathVariable("id") Long id) throws ProductNotFoundException {

        if(productRepository.findById(id).isEmpty()){
            throw new ProductNotFoundException(String.format("Produkt z indeksem [%d] nie był znaleziony", id));
        }
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, Product product) throws ProductNotFoundException {
        if(productRepository.findById(id).isEmpty()){
            throw new ProductNotFoundException(String.format("Produkt z indeksem [%d] nie był znaleziony", id));
        }
        productRepository
                .findById(id)
                .ifPresent(updatedProduct -> {
                    updatedProduct.setName(product.getName());
                    updatedProduct.setDescription(product.getDescription());
                    updatedProduct.setCategory(product.getCategory());
                    updatedProduct.setPrice(product.getPrice());
                    productRepository.save(updatedProduct);
                });
    }
}
