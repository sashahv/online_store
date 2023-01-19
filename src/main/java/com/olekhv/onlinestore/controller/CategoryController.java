package com.olekhv.onlinestore.controller;

import com.olekhv.onlinestore.entity.Category;
import com.olekhv.onlinestore.entity.Product;
import com.olekhv.onlinestore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> showAllCategory() {
        List<Category> categories = categoryService.showAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Long id) {
        Category category = categoryService.getCategoryByID(id);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<?> fetchAllProductsFromCategory(@PathVariable("id") Long categoryId){
        List<Product> products = categoryService.fetchAllProductsFromCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("")
    public ResponseEntity<?> createNewCategory(@RequestBody Category category) {
            categoryService.createNewCategory(category);
            return ResponseEntity.ok("created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(
                    String.format("Categoria: \"%s\" została usunięta",
                            categoryService.getCategoryByID(id).getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @RequestBody Category category){
        Category updatedCategory = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updatedCategory);
    }
}
