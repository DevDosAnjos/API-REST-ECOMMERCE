package com.app.ecommerce.repositories;

import com.app.ecommerce.entities.Purchase;
import com.app.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer>{
    Optional<Purchase> findById(Integer id);
    Optional<Purchase> findByIdAndUser(Integer id,User user);
    List<Purchase> findAllByUser(User user);
}
