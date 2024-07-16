package com.app.labdesoftware.repository;

import com.app.labdesoftware.entities.Category;
import com.app.labdesoftware.entities.Product;
import com.app.labdesoftware.enumeration.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    List<Product> findByStock(Stock stock);
    Optional<Product> findById(int ids);
    List<Product> findByCategory(Category category);
    List<Product> findByBrand(String brand);

}
