package com.app.labdesoftware.service;

import com.app.labdesoftware.controller.category.CategoryRequest;
import com.app.labdesoftware.controller.category.CategoryResponse;
import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.enumeration.StatusCategory;
import com.app.labdesoftware.repository.CategoryRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<CategoryResponse> listCategories() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryResponse.fromCategory(categories);
    }

    public CategoryResponse getCategory(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            return new CategoryResponse(
                    category.get().getId(),
                    category.get().getName(),
                    category.get().getStatus()

            );
        }
        return null;
    }

    public List<CategoryResponse> listDisableCategories() {
        List<Category> categories = categoryRepository.findAllByStatus(StatusCategory.DISABLE);
        return CategoryResponse.fromCategory(categories);
    }

    public Category create(CategoryRequest categoryRequest) {
        Category category = Category.from(categoryRequest);
        return categoryRepository.save(category);
    }

    public void update(int id, CategoryRequest categoryRequest) throws BadRequestException {
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent()){
            throw new BadRequestException("CATEGORY ID NOT FOUND");
        }
        if (categoryRequest.name() != null) {
            category.get().setName(categoryRequest.name());
        }
        categoryRepository.saveAndFlush(category.get());
    }

    public void delete(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            category.get().setStatus(StatusCategory.DISABLE);
            categoryRepository.saveAndFlush(category.get());
        }
    }
}
