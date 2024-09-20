package com.app.ecommerce.repositories;

import com.app.ecommerce.entities.Category;
import com.app.ecommerce.entities.Product;
import com.app.ecommerce.enumerations.StatusStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    Optional<Product> findById(Integer id);
    List<Product> findAllByStatusStock(StatusStock statusStock);
    List<Product> findAllByCategory(Optional<Category> category);
    List<Product> findAllByPrice(Integer price);
    List<Product> findAllByBrand(String brand);
    List<Product> findAllByCategoryAndStatusStock(Optional<Category> category, StatusStock statusStock);
    List<Product> findAllByPriceAndStatusStock(Integer price, StatusStock statusStock);
    List<Product> findAllByBrandAndStatusStock(String brand, StatusStock statusStock);
}
