package com.health_project.server.controllers; // Assuming this is your package structure

import com.health_project.server.dto.HomeResponseDto;
import com.health_project.server.dto.LoginRegRequestDto;
import com.health_project.server.dto.LoginRequestDto;
import com.health_project.server.entities.UserEntity;
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

    private final AuthService authService; // Inject AuthService

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody LoginRegRequestDto registerRequest) {
        try {
            UserEntity registeredUser = authService.register(registerRequest.getUsername(), registerRequest.getPassword());
            // In a real app, you might return a DTO (Data Transfer Object) instead of the full UserEntity
            // or perhaps a simplified success message.
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    Map.of("message", "Registration successful", "userId", registeredUser.getId(), "username", registeredUser.getUsername())
            );
        } catch (IllegalArgumentException e) {
            // Catch specific business logic errors (e.g., username already exists)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // Catch any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred during registration."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        Optional<UserEntity> userOptional = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(Map.of("message", "Login successful", "username", userOptional.get().getUsername()));
        } else {
            // Authentication failed (invalid credentials)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password."));
        }
    }



    @GetMapping("/home")
    public ResponseEntity<HomeResponseDto> home() {
        HomeResponseDto response = new HomeResponseDto();
        response.setResponse("Success");

        System.out.println("Home Response");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
