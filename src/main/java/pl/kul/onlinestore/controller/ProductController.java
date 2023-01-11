package pl.kul.onlinestore.controller;

import lombok.extern.log4j.Log4j2;
import pl.kul.onlinestore.entity.Product;
import pl.kul.onlinestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
            productService.addProduct(product);
            return ResponseEntity.ok(String.format("Produkt: \"%s\" został dodany", product.getName()));
    }

    @GetMapping
    public ResponseEntity<?> showAllProducts() {
        List<Product> products = productService.showAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchProductById(@PathVariable Long id) {
        Product product = productService.fetchProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(String.format("Produkt z indeksem: %d został usuniety", id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product){
        productService.updateProduct(id, product);
        return ResponseEntity.ok(String.format("Produkt z indeksem: %d został edytowany", id));
    }
}
