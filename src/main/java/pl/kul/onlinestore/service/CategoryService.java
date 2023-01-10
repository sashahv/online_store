package pl.kul.onlinestore.service;

import pl.kul.onlinestore.entity.Category;
import pl.kul.onlinestore.exception.BadRequestException;
import pl.kul.onlinestore.exception.CategoryAlreadyExistsException;
import pl.kul.onlinestore.exception.CategoryNotFoundException;
import pl.kul.onlinestore.repository.CategoryRepository;
import pl.kul.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> showAllCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.
                findAll().
                forEach(categories::add);
        return categories;
    }

    public Category getCategoryByID(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String.format("Kategoria z indeksem [%d] nie była znaleziona", id)));
    }

    public void createNewCategory(Category category) {
        String name = category.getName();
        if (categoryRepository.findCategoryByName(name).isPresent())
            throw new CategoryAlreadyExistsException(String.format("Kategoria z nazwą \"%s\" już istnieje", name));
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException(String.format("Kategoria z indeksem [%d] nie była znaleziona", id)));
        categoryRepository.deleteById(id);
    }

//    public ResponseEntity<Void> updateCategory(Long id, Category category) {
//
//        if (categoryRepository.findById(id).isEmpty()) {
//            throw new BadRequestException("Category with index:" + id + " does not exist");
//        }
//        categoryRepository
//                .findById(id)
//                .ifPresent(updatedCategory -> {
//                    updatedCategory.setName(category.getName());
//                    updatedCategory.setDescription(category.getDescription());
//                    categoryRepository.save(updatedCategory);
//                });
//        HttpHeaders header = new HttpHeaders();
//        header.add("description", "Updating category");
//
//        return ResponseEntity.status(HttpStatus.OK).headers(header).build();
//    }
}
