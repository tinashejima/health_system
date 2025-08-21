package com.health_project.server.config;

import com.health_project.server.entities.Role;
import com.health_project.server.repositories.RoleRepository;
import com.health_project.server.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Configuration
public class AppSeeder implements CommandLineRunner {

    private final UserService userService;
    private final RoleRepository roleRepository;

    public AppSeeder(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Seed roles
        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }
        if (roleRepository.findByName("USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

        // Seed admin user
        try {
            Set<String> adminRoles = new HashSet<>();
            adminRoles.add("ADMIN");
            userService.registerUser("admin@clinic.co.zw", "Password1", "admin@gmail.com",adminRoles);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
