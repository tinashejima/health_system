package com.health_project.server.controllers;

import com.health_project.server.dto.HomeResponseDto;
import com.health_project.server.dto.JwtResponseDto;
import com.health_project.server.dto.LoginRegRequestDto;
import com.health_project.server.dto.LoginRequestDto;
import com.health_project.server.entities.User;
import com.health_project.server.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody LoginRegRequestDto registerRequest) {
        try {
            User registeredUser = authService.register(registerRequest.getUsername(), registerRequest.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("message", "Registration successful", "userId", registeredUser.getId(), "username", registeredUser.getUsername())
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred during registration."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        Optional<String> jwtOptional = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (jwtOptional.isPresent()) {
            return ResponseEntity.ok(new JwtResponseDto(jwtOptional.get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password."));
        }
    }

    @GetMapping("/home")
    public ResponseEntity<HomeResponseDto> home() {
        HomeResponseDto response = new HomeResponseDto();
        response.setResponse("Welcome to the home page!");

        System.out.println("Home Response");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
