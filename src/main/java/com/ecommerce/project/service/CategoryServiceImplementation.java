package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {

    List<Category> categories = new ArrayList<>();
    Long categoriesIdCounter = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void addCategory(Category category) {
        category.setCategoryId(categoriesIdCounter++);
        categories.add(category);
    }
    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource is not found!"));
        categories.remove(category);
        return "Category with id " + categoryId + " is deleted";
                        
    }

    @Override
    public Category updaateCategory(Long categoryId, Category category) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        if(optionalCategory.isPresent()){
            Category updatedCategory = optionalCategory.get();
            updatedCategory.setCategoryName(category.getCategoryName());
            return updatedCategory;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no category with id "+ categoryId);
        }
    }
}
