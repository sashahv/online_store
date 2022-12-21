package com.store.onlinestore.controller;

import com.store.onlinestore.entity.Product;
import com.store.onlinestore.exception.CategoryNotFoundException;
import com.store.onlinestore.exception.ProductNotFoundException;
import com.store.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity addProduct(@RequestBody Product product) {
        try {
            productService.addProduct(product);
            return ResponseEntity.ok().body("Produkt został dodany");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Nastąpił jakiś błąd");
        }
    }

    @GetMapping
    public ResponseEntity showAllProducts() {
        try {
            return ResponseEntity.ok(productService.showAllProducts());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }  catch (Exception e) {
            return ResponseEntity.badRequest().body("Nastąpił jakiś błąd");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.getProductById(id));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Nastąpił jakiś błąd");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok()
                    .body(String.format("Produkt: \"%s\" został usunięty",
                            productService.getProductById(id).get().getName()));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProduct(@PathVariable Long id, @RequestBody Product product){
        try {
            productService.updateProduct(id, product);
            return ResponseEntity.ok().body(String.format("Zmiany do produktu \"[%s]\" zostały zapisane", product.getName()));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
