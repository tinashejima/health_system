package com.health_project.server.services;

import com.health_project.server.entities.Role;
import com.health_project.server.entities.User;
import com.health_project.server.repositories.RoleRepository;
import com.health_project.server.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User registerUser(String username, String password, String email) {
        return registerUser(username, password, email, new HashSet<>()); // Call the overloaded method with empty roles
    }

    public User registerUser(String username, String password, String email, Set<String> roles) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty.");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username '" + username + "' already exists. Please choose another.");
        }

        User newUser = new User(username, passwordEncoder.encode(password), email);

        Set<Role> userRoles = new HashSet<>();
        if (roles == null || roles.isEmpty()) {
            // Assign default USER role if no roles are specified
            roleRepository.findByName("USER").ifPresent(userRoles::add);
        } else {
            for (String roleName : roles) {
                roleRepository.findByName(roleName).ifPresent(userRoles::add);
            }
        }
        newUser.setRoles(userRoles);
        return userRepository.save(newUser);
    }

    public Optional<User> loginUser(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            System.out.println("Login failed: Username and password cannot be empty.");
            return Optional.empty();
        }

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
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

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean deleteUser(String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
