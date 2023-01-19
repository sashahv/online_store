package com.olekhv.onlinestore.service;

import com.olekhv.onlinestore.entity.Category;
import com.olekhv.onlinestore.entity.Product;
import com.olekhv.onlinestore.exception.category.CategoryAlreadyExistsException;
import com.olekhv.onlinestore.exception.category.CategoryNotFoundException;
import com.olekhv.onlinestore.repository.CategoryRepository;
import com.olekhv.onlinestore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
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

    public List<Product> fetchAllProductsFromCategory(Long id) {
        List<Product> products = productRepository.findAllProductsByCategoryId(id);
        return products;
    }

    public Category updateCategory(Long id, Category category) {
        categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException(String.format("Kategoria z indeksem [%d] nie była znaleziona", id))
        );

        Category updatedCategory = new Category();
        updatedCategory.setName(category.getName());
        updatedCategory.setDescription(category.getDescription());
        categoryRepository.save(updatedCategory);
        return updatedCategory;
    }
}
