package com.app.ecommerce.entities;

import com.app.ecommerce.controllers.category.CategoryRequest;
import com.app.ecommerce.enumerations.StatusCategory;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private StatusCategory statusCategory;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
        this.statusCategory = StatusCategory.ACTIVE;
    }

    public static Category from(CategoryRequest categoryRequest) {
        return new Category(
                categoryRequest.name()
        );
    }
}
