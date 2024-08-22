package com.app.labdesoftware.repositories;

import com.app.labdesoftware.entities.Order;
import com.app.labdesoftware.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findById(Integer id);
    Optional<Order> findByUser(User user);
    List<Order> findAllByUser(User user);
}
