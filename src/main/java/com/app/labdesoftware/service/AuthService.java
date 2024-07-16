package com.app.labdesoftware.service;

import com.app.labdesoftware.entities.User;
import com.app.labdesoftware.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean register(String username, String password){
        if (userRepository.findByUsername(username) != null){
            return false;
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }

    public void update(int id, User updatedUser) throws BadRequestException {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()){
            throw new BadRequestException("USER IS NOT FOUND");
        }
        User user = foundUser.get();
        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());
        userRepository.save(user);
    }
}
