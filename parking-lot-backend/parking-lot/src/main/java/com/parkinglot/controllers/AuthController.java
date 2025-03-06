package com.parkinglot.controllers;

import com.parkinglot.dtos.AuthRequest;
import com.parkinglot.dtos.AuthResponse;
import com.parkinglot.dtos.UserDTO;
import com.parkinglot.models.User;
import com.parkinglot.repositories.UserRepository;
import com.parkinglot.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        logger.info("Login attempt for username: {}", request.getUsername());
        try {
            AuthResponse response = authService.authenticate(request);
            logger.info("Login successful for username: {}, response: jwtToken={}, role={}", 
                        request.getUsername(), response.getJwtToken(), response.getRole());
            ResponseEntity<AuthResponse> responseEntity = ResponseEntity.ok(response);
            logger.info("ResponseEntity created successfully for username: {}", request.getUsername());
            return responseEntity;
        } catch (RuntimeException e) {
            logger.error("Login failed for username: {} - {}", request.getUsername(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (Exception e) {
            logger.error("Unexpected error during login for username: {} - {}", request.getUsername(), e.getMessage(), e);
            throw e; // Re-throw to capture full stack trace
        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO userDTO) {
        logger.info("Register attempt for username: {}", userDTO.getUsername());
        try {
            AuthResponse response = authService.register(userDTO);
            logger.info("Registration successful for username: {}", userDTO.getUsername());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Registration failed for username: {} - {}", userDTO.getUsername(), e.getMessage());
            throw e;
        }
    }

    @GetMapping("/users/me")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        logger.info("Fetching profile for current user");
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                logger.error("User not found: {}", username);
                return new RuntimeException("User not found");
            });
        logger.info("Profile fetched successfully for user: {}", username);
        return ResponseEntity.ok(UserDTO.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole())
            .build());
    }

    @PutMapping("/users/me")
    public ResponseEntity<UserDTO> updateCurrentUser(@RequestBody UserDTO userDTO, Authentication authentication) {
        logger.info("Updating profile for current user");
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> {
                logger.error("User not found: {}", username);
                return new RuntimeException("User not found");
            });
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
        logger.info("Profile updated successfully for user: {}", username);
        return ResponseEntity.ok(UserDTO.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .role(user.getRole())
            .build());
    }
}