package com.app.labdesoftware.controllers.category;

import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements com.app.labdesoftware.controllers.category.Category {

    @Autowired
    private CategoryService categoryService;

    public ResponseEntity<CategoryResponse> get(User user,Integer id){
        CategoryResponse category = categoryService.getCategory(user,id);
        return ResponseEntity.ok(category);
    }

    public ResponseEntity<List<CategoryResponse>> listAll(User user){
        List<CategoryResponse> categories = categoryService.listAllCategories(user);
        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<List<CategoryResponse>> listActive(){
        List<CategoryResponse> categories = categoryService.listActiveCategories();
        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<List<CategoryResponse>> listInactive(){
        List<CategoryResponse> categories = categoryService.listInactiveCategories();
        return ResponseEntity.ok(categories);
    }

    public ResponseEntity<Category> create(CategoryRequest categoryRequest){
        Category category = categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(category);
    }

    public ResponseEntity<Category> update(Integer id, CategoryRequest categoryRequest) {
        Category category = categoryService.updateCategory(id,categoryRequest);
        return ResponseEntity.ok(category);
    }

    public ResponseEntity<Category> delete(Integer id) {
        Category category = categoryService.deleteCategory(id);
        return ResponseEntity.ok(category);
    }
}
