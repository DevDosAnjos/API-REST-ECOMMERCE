package com.app.labdesoftware.repositories;

import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumerations.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    List<User> findByRole(UserRole userRole);
}