package com.health_project.server.repositories;

import com.health_project.server.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity save(UserEntity user); // Changed type to UserEntity


    Optional<UserEntity> findByUsername(String username); // Changed type to UserEntity

    Optional<UserEntity> findById(String id); // Changed type to UserEntity

    List<UserEntity> findAll(); // Changed type to UserEntity


}


