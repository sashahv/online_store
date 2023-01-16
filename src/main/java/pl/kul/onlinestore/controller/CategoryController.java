package pl.kul.onlinestore.controller;

import pl.kul.onlinestore.entity.Category;
import pl.kul.onlinestore.service.CategoryService;
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

    @GetMapping("")
    public ResponseEntity<?> showAllCategory() {
        List<Category> categories = categoryService.showAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryByID(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping("")
    public ResponseEntity<?> createNewCategory(@RequestBody Category category) {
            categoryService.createNewCategory(category);
            return ResponseEntity.ok("created");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok(
                    String.format("Categoria: \"%s\" została usunięta",
                            categoryService.getCategoryByID(id).getName()));
    }
}
