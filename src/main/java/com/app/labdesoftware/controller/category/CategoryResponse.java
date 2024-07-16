package com.app.labdesoftware.controller.category;

import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.enumeration.StatusCategory;

import java.util.List;

public record CategoryResponse(
        int id,
        String name,
        StatusCategory status

) {
    public static List<CategoryResponse> fromCategory(List<Category> categories){
        return categories.stream().map(category -> new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getStatus()
        )).toList();
    }
}
