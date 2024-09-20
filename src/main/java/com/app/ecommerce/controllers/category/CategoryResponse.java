package com.app.ecommerce.controllers.category;

import com.app.ecommerce.entities.Category;
import com.app.ecommerce.enumerations.StatusCategory;

import java.util.List;

public record CategoryResponse(
        Integer id,
        String name,
        StatusCategory status
) {
    public static List<CategoryResponse> fromCategory(List<Category> categories){
        return categories.stream().map(category -> new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getStatusCategory()
        )).toList();
    }
}
