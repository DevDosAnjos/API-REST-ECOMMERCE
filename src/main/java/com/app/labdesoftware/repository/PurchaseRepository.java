package com.app.labdesoftware.repository;

import com.app.labdesoftware.entities.Purchase;
import com.app.labdesoftware.enumeration.StatusPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Integer>{
    List<Purchase> findByStatusPurchase(StatusPurchase statusPurchase);
    List<Purchase> findAllByClientId(int id);
    Optional<Purchase> findById(int id);
}
