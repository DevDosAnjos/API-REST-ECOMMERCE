package com.app.labdesoftware.entities;

import com.app.labdesoftware.controller.category.CategoryRequest;
import com.app.labdesoftware.enumeration.StatusCategory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "categories")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
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
