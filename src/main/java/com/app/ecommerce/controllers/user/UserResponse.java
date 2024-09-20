package com.app.ecommerce.controllers.user;

import com.app.ecommerce.entities.User;
import com.app.ecommerce.enumerations.UserRole;

import java.util.List;

public record UserResponse(
        Integer id,
        String username,
        UserRole userRole
) {
    public static List<UserResponse> fromUser(List<User> users) {
        return users.stream().map(user -> new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        )).toList();
    }
}
