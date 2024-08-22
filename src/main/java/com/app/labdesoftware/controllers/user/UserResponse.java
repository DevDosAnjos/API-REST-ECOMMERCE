package com.app.labdesoftware.controllers.user;

import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.enumerations.UserRole;

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
