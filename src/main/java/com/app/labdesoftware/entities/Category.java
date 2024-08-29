package com.app.labdesoftware.entities;

import com.app.labdesoftware.controllers.category.CategoryRequest;
import com.app.labdesoftware.enumerations.StatusCategory;
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
    private StatusCategory status;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
        this.status = StatusCategory.ACTIVE;
    }

    public static Category from(CategoryRequest categoryRequest) {
        return new Category(
                categoryRequest.name()
        );
    }
}
