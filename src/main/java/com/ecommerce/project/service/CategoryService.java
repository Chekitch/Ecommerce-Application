package com.ecommerce.project.service;


import com.ecommerce.project.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    void addCategory(Category category);
    String deleteCategory(Long categoryId);
    Category updaateCategory(Long categoryId, Category category);

}
