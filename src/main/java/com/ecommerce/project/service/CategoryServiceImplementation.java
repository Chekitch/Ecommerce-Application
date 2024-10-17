package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class CategoryServiceImplementation implements CategoryService {


    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty())
            throw new APIException("There are no categories");
        return categories;
    }

    @Override
    public void addCategory(Category category) {
        if(categoryRepository.findByCategoryName(category.getCategoryName()) != null) {
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists");
        }else if(category.getCategoryId() != null && categoryRepository.findById(category.getCategoryId()).isPresent()) {
            throw new APIException("Category with the id " + category.getCategoryId() + " already exists");
        }
        categoryRepository.save(category);
    }
    @Override
    public String deleteCategory(Long categoryId){

        /*Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource is not found!"));*/

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","categoryId", categoryId));
        categoryRepository.delete(category);

        return "Category with categoryId {" + categoryId + "} deleted!";
                        
    }

    @Override
    public Category updaateCategory(Long categoryId, Category category) {

        // Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        Category savedCategory = categoryRepository.findById(categoryId).orElseThrow(() -> {
            return new ResourceNotFoundException("Category","categoryId", categoryId);
        });

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
