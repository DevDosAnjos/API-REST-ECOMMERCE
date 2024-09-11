package com.app.labdesoftware.UnitTests;

import com.app.labdesoftware.controllers.category.CategoryRequest;
import com.app.labdesoftware.controllers.category.CategoryResponse;
import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumerations.StatusCategory;
import com.app.labdesoftware.enumerations.UserRole;
import com.app.labdesoftware.repositories.CategoryRepository;
import com.app.labdesoftware.services.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("Should get a category")
    void getCategory() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        Category category = new Category("Test");

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        CategoryResponse response = categoryService.getCategory(user,category.getId());

        Assertions.assertEquals(category.getId(),response.id());
        Assertions.assertEquals(category.getName(),response.name());
        Assertions.assertEquals(category.getStatusCategory(),response.status());

    }

    @Test
    @DisplayName("Verify if the category exists, if not throws an exception")
    void getCategoryException() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        Assertions.assertThrows(ResponseStatusException.class,() -> categoryService.getCategory(user,any()));
    }

    @Test
    @DisplayName("Verity if authenticated user has role User, and throws a exception")
    void getCategoryException2() {
        User user = new User("User","User", UserRole.USER);
        Category category = new Category("Category 1");
        category.setStatusCategory(StatusCategory.INACTIVE);

        Assertions.assertThrows(ResponseStatusException.class,() -> categoryService.getCategory(user,category.getId()));
    }

    @Test
    @DisplayName("Should get a list of all categories")
    void listAllCategories() {
        User user = new User("Admin","Admin", UserRole.ADMIN);
        Category category = new Category("Category 1");
        Category category2 = new Category("Category 2");
        Category category3 = new Category("Category 3");
        Category category4 = new Category("Category 4");

        when(categoryRepository.findAll()).thenReturn(List.of(category,category2,category3,category4));
        List<CategoryResponse> responses = categoryService.listAllCategories(user);

        Assertions.assertEquals(4,responses.size());
    }

    @Test
    @DisplayName("Should get a list of all active categories")
    void listActiveCategories() {
        Category category = new Category("Category 1");
        Category category2 = new Category("Category 2");
        Category category3 = new Category("Category 3");
        Category category4 = new Category("Category 4");
        category3.setStatusCategory(StatusCategory.INACTIVE);
        category4.setStatusCategory(StatusCategory.INACTIVE);

        when(categoryRepository.findAllByStatusCategory(StatusCategory.ACTIVE)).thenReturn(List.of(category,category2));
        List<CategoryResponse> responses = categoryService.listActiveCategories();

        Assertions.assertEquals(2,responses.size());
    }

    @Test
    @DisplayName("Should get a list of all inactive categories")
    void listInactiveCategories() {
        Category category = new Category("Category 1");
        Category category2 = new Category("Category 2");
        Category category3 = new Category("Category 3");
        Category category4 = new Category("Category 4");
        category3.setStatusCategory(StatusCategory.INACTIVE);
        category4.setStatusCategory(StatusCategory.INACTIVE);

        when(categoryRepository.findAllByStatusCategory(StatusCategory.INACTIVE)).thenReturn(List.of(category3,category4));
        List<CategoryResponse> responses = categoryService.listInactiveCategories();

        Assertions.assertEquals(2,responses.size());
    }

    @Test
    @DisplayName("Should create a new category")
    void createCategory() {
        CategoryRequest categoryRequest = new CategoryRequest("Test", StatusCategory.ACTIVE);
        Category category = new Category(categoryRequest.name());

        when(categoryRepository.save(category)).thenReturn(category);
        Category response = categoryService.createCategory(categoryRequest);

        Assertions.assertEquals(categoryRequest.name(),response.getName());
    }

    @Test
    @DisplayName("Should update a category")
    void updateCategory() {
        Category category = new Category("Category 1");
        CategoryRequest categoryRequest = new CategoryRequest("Test Update",StatusCategory.ACTIVE);

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(categoryRepository.saveAndFlush(category)).thenReturn(category);
        Category response = categoryService.updateCategory(category.getId(),categoryRequest);

        Assertions.assertEquals(response,category);
    }

    @Test
    @DisplayName("Verify if the category exists, if not throws an exception")
    void updateCategoryException() {
        CategoryRequest categoryRequest = new CategoryRequest("Test Update",StatusCategory.ACTIVE);

        Assertions.assertThrows(ResponseStatusException.class, ()-> categoryService.updateCategory(any(), categoryRequest));
    }

    @Test
    @DisplayName("Should disable a category")
    void deleteCategory() {
        Category category = new Category("Category 1");

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(categoryRepository.saveAndFlush(any())).thenReturn(category);
        Category response = categoryService.deleteCategory(category.getId());

        Assertions.assertEquals(StatusCategory.INACTIVE,response.getStatusCategory());
    }

    @Test
    @DisplayName("Verify if the category exists, if not throws an exception")
    void deleteCategoryException() {
        Assertions.assertThrows(ResponseStatusException.class, ()-> categoryService.deleteCategory(any()));
    }
}