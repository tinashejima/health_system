package com.health_project.server.services;

import com.health_project.server.entities.UserEntity; // Explicitly import your UserEntity
import org.springframework.stereotype.Service; // Assuming Spring Boot context

import java.util.Optional;

@Service // Mark as a Spring service component
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public UserEntity register(String username, String password) {
        return userService.registerUser(username, password);
    }

    public Optional<UserEntity> authenticate(String username, String password) {
        return userService.loginUser(username, password);
    }

}
