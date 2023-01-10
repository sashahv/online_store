package pl.kul.onlinestore.service;

import pl.kul.onlinestore.entity.Product;
import pl.kul.onlinestore.exception.ProductNotFoundException;
import pl.kul.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> showAllProducts(){
        List<Product> products = new ArrayList<>();
        productRepository
                .findAll()
                .forEach(products::add);
        if (products.isEmpty()) {
            throw new ProductNotFoundException("Nie znaleziono żadnych produktów");
        }
        return products;
    }

    public List<Product> showProductsByCategory(Long categoryId){
        List<Product> products = productRepository.findAllByCategoryId(categoryId);
        if(products.isEmpty()) throw new ProductNotFoundException("Nie ma żadnych produktów z tej kategorii");
        return products;
    }

    public Product fetchProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Produkt z indeksem [%d] nie był znaleziony", id)));
    }

    public Product fetchProductByNameContains(String name) {
        return productRepository.findProductByNameContains(name)
                .orElseThrow(() -> new ProductNotFoundException("Nie znaleziono żadnych produktów z taką nazwą"));
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(@PathVariable("id") Long id) {
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Produkt z indeksem [%d] nie był znaleziony", id)));
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, Product product){
        productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Produkt z indeksem [%d] nie był znaleziony", id)));
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
