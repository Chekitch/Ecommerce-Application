package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CategoryServiceImplementation implements CategoryService {

    Long categoriesIdCounter = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Category category) {
        category.setCategoryId(categoriesIdCounter++);
        categoryRepository.save(category);
    }
    @Override
    public String deleteCategory(Long categoryId){

        /*Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource is not found!"));*/

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found!"));
        categoryRepository.delete(category);

        return "Category with categoryId {" + categoryId + "} deleted!";
                        
    }

    @Override
    public Category updaateCategory(Long categoryId, Category category) {

        // Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found!"));

        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);

        return savedCategory;

        /*Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        if(optionalCategory.isPresent()){
            Category updatedCategory = optionalCategory.get();
            updatedCategory.setCategoryName(category.getCategoryName());
            return updatedCategory;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no category with id "+ categoryId);
        }*/
    }
}
