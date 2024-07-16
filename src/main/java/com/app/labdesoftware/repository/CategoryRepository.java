package com.app.labdesoftware.repository;

import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.enumeration.StatusCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByStatus(StatusCategory status);
    Optional<Category> findById(int id);

}
