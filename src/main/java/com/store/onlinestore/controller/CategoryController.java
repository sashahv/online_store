package com.store.onlinestore.controller;

import com.store.onlinestore.entity.Category;
import com.store.onlinestore.exception.CategoryAlreadyExistsException;
import com.store.onlinestore.exception.CategoryNotFoundException;
import com.store.onlinestore.repository.CategoryRepository;
import com.store.onlinestore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity showAllCategory() {
        try {
            return ResponseEntity.ok(categoryService.showAllCategories());
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getCategoryById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(categoryService.getCategoryByID(id));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity createNewCategory(@RequestBody Category category) {
        try {
            categoryService.createNewCategory(category);
            return ResponseEntity.ok().build();
        } catch (CategoryAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok()
                    .body(String.format("Categoria: \"%s\" została usunięta",
                            categoryService.getCategoryByID(id).get().getName()));
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
