package com.health_project.server.services;

import com.health_project.server.entities.UserEntity;
import com.health_project.server.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity registerUser(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty.");
        }
        if (userRepository.findByUsername(username).isPresent()) { // findByUsername is a custom method
            throw new IllegalArgumentException("Username '" + username + "' already exists. Please choose another.");
        }

        UserEntity newUser = new UserEntity(username, password);
        return userRepository.save(newUser);
    }

    public Optional<UserEntity> loginUser(String username, String password) { // Ensure return type is Optional<UserEntity>
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            System.out.println("Login failed: Username and password cannot be empty.");
            return Optional.empty();
        }

        Optional<UserEntity> userOptional = userRepository.findByUsername(username); // findByUsername is a custom method

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (user.validatePassword(password)) {
                System.out.println("Login successful for user: " + username);
                return Optional.of(user);
            } else {
                System.out.println("Login failed: Incorrect password for user: " + username);
            }
        } else {
            System.out.println("Login failed: User '" + username + "' not found.");
        }
        return Optional.empty();
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean deleteUser(String id) {
        // Check if the user exists before attempting to delete
        if (userRepository.existsById(id)) { // JpaRepository's existsById method
            userRepository.deleteById(id); // JpaRepository's deleteById method returns void
            return true; // Deletion was attempted and user existed
        }
        return false;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
}
