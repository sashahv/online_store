package com.store.onlinestore.service;

import com.store.onlinestore.entity.Category;
import com.store.onlinestore.exception.BadRequestException;
import com.store.onlinestore.exception.CategoryAlreadyExistsException;
import com.store.onlinestore.exception.CategoryNotFoundException;
import com.store.onlinestore.repository.CategoryRepository;
import com.store.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository1) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository1;
    }

    public List<Category> showAllCategories() throws CategoryNotFoundException {
        List<Category> categories = new ArrayList<>();
        categoryRepository.
                findAll().
                forEach(categories::add);
        if (categories.isEmpty()) throw new CategoryNotFoundException("Jeszce nie ma żadnych kategorii");
        return categories;
    }

    public Optional<Category> getCategoryByID(Long id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty())
            throw new CategoryNotFoundException(String.format("Kategoria z indeksem [%d] nie była znaleziona", id));
        return category;
    }

    public void createNewCategory(Category category) throws CategoryAlreadyExistsException {
        String name = category.getName();
        if (!(categoryRepository.findCategoryByName(name) == null))
            throw new CategoryAlreadyExistsException(String.format("Kategoria z nazwą \"%s\" już istnieje", name));
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()) {
            throw new CategoryNotFoundException(String.format("Kategoria z indeksem [%d] nie była znaleziona", id));
        }
        categoryRepository.deleteById(id);
    }

    public ResponseEntity<Void> updateCategory(Long id, Category category) {

        if (categoryRepository.findById(id).isEmpty()) {
            throw new BadRequestException("Category with index:" + id + " does not exist");
        }
        categoryRepository
                .findById(id)
                .ifPresent(updatedCategory -> {
                    updatedCategory.setName(category.getName());
                    updatedCategory.setDescription(category.getDescription());
                    categoryRepository.save(updatedCategory);
                });
        HttpHeaders header = new HttpHeaders();
        header.add("description", "Updating category");

        return ResponseEntity.status(HttpStatus.OK).headers(header).build();
    }
}
