package com.app.labdesoftware.controller.category;

import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.service.CategoryService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("id") int id){
        CategoryResponse category = categoryService.getCategory(id);
        return ResponseEntity.ok(category);
    }

   @GetMapping("/all")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        List<CategoryResponse> categories = categoryService.listCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/disable")
    public ResponseEntity<List<CategoryResponse>> getDisableCategories(){
        List<CategoryResponse> categories = categoryService.listDisableCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping()
    public ResponseEntity<CategoryResponse> createCategory(@RequestBody CategoryRequest categoryRequest){
        Category category = categoryService.create(categoryRequest);
        CategoryResponse response = new CategoryResponse(category.getId(),category.getName(),category.getStatus());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCategory(@PathVariable("id") int id, @RequestBody CategoryRequest categoryRequest) throws  BadRequestException {
        categoryService.update(id,categoryRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") int id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }
}
