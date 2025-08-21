package com.health_project.server.services;

import com.health_project.server.config.JwtUtil;
import com.health_project.server.entities.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public User register(String username, String password, String email) {
        return userService.registerUser(username, password, email, new HashSet<>());
    }

    public Optional<String> authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            final UserDetails userDetails = userService.loadUserByUsername(username);
            final String jwt = jwtUtil.generateToken(userDetails);
            return Optional.of(jwt);
        } catch (AuthenticationException e) {
            return Optional.empty();
        }
    }
}
