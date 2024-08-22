package com.app.labdesoftware.repositories;

import com.app.labdesoftware.entities.Purchase;
import com.app.labdesoftware.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer>{
    Optional<Purchase> findById(Integer id);
    Optional<Purchase> findByUser(User user);
    List<Purchase> findAllByUser(User user);
}
