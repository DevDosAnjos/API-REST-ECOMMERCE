package com.app.labdesoftware.services;

import com.app.labdesoftware.controllers.category.CategoryRequest;
import com.app.labdesoftware.controllers.category.CategoryResponse;
import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumerations.StatusCategory;
import com.app.labdesoftware.enumerations.UserRole;
import com.app.labdesoftware.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryResponse getCategory(User user,Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"CATEGORY NOT FOUND, ID IS INCORRECT");
        }
        if (user.getRole().equals(UserRole.USER) && category.get().getStatusCategory() == StatusCategory.INACTIVE) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CATEGORY INACTIVE, ENTER ANOTHER ID ");
        }
        return new CategoryResponse(
                category.get().getId(),
                category.get().getName(),
                category.get().getStatusCategory()
        );
    }

    public List<CategoryResponse> listAllCategories(User user) {
        List<Category> categories;
        if (user.getRole().equals(UserRole.USER)) {
            categories = categoryRepository.findAllByStatusCategory(StatusCategory.ACTIVE);
        } else {
            categories = categoryRepository.findAll();
        }
        return CategoryResponse.fromCategory(categories);
    }

    public List<CategoryResponse> listActiveCategories() {
        List<Category> categories = categoryRepository.findAllByStatusCategory(StatusCategory.ACTIVE);
        return CategoryResponse.fromCategory(categories);
    }

    public List<CategoryResponse> listInactiveCategories() {
        List<Category> categories = categoryRepository.findAllByStatusCategory(StatusCategory.INACTIVE);
        return CategoryResponse.fromCategory(categories);
    }

    public Category createCategory(CategoryRequest categoryRequest) {
        Category category = Category.from(categoryRequest);
        return categoryRepository.save(category);
    }

    public Category updateCategory(Integer id, CategoryRequest categoryRequest) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"CATEGORY NOT FOUND, ID IS INCORRECT");
        }
        if (categoryRequest.name() != null) {
            category.get().setName(categoryRequest.name());
        }
        if (categoryRequest.statusCategory() != null){
            category.get().setStatusCategory(categoryRequest.statusCategory());
        }
        return categoryRepository.saveAndFlush(category.get());
    }

    public Category deleteCategory(Integer id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"CATEGORY NOT FOUND, ID IS INCORRECT");
        }
        category.get().setStatusCategory(StatusCategory.INACTIVE);
        return categoryRepository.saveAndFlush(category.get());
    }
}
