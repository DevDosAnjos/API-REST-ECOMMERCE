package com.app.ecommerce.repositories;

import com.app.ecommerce.entities.Category;
import com.app.ecommerce.enumerations.StatusCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(Integer id);
    List<Category> findAllByStatusCategory(StatusCategory statusCategory);
}
