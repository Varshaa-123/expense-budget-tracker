package com.wipro.expense.service;

import com.wipro.expense.entity.Category;
import com.wipro.expense.exception.ResourceNotFoundException;
import com.wipro.expense.repository.CategoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new IllegalArgumentException("Category '" + category.getCategoryName() + "' already exists");
        }
        category.setCategoryId(null);
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }

    public Category updateCategory(Long id, Category newData) {
        Category category = getCategoryById(id);
        category.setCategoryName(newData.getCategoryName());
        category.setCategoryType(newData.getCategoryType());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.delete(getCategoryById(id));   // fails with 409 if transactions use it
    }
}
