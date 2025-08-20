package com.health_project.server.repositories;

import com.health_project.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User save(User user); // Changed type to UserEntity


    Optional<User> findByUsername(String username); // Changed type to UserEntity

    Optional<User> findById(String id); // Changed type to UserEntity

    List<User> findAll(); // Changed type to UserEntity


}


