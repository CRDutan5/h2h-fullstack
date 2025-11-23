package com.example.h2h_project.controller;

import com.example.h2h_project.dto.AuthResponse;
import com.example.h2h_project.model.User;
import com.example.h2h_project.repository.UserRepository;
import com.example.h2h_project.requests.LoginRequest;
import com.example.h2h_project.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationController(UserRepository userRepository,
                                     BCryptPasswordEncoder passwordEncoder,
                                     JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Login endpoint - authenticates user and returns JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        User user = userOptional.get();

        // Verify password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        // Create response
        AuthResponse authResponse = new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getFirstName(),
                user.getLastName()
        );

        return ResponseEntity.ok(authResponse);
    }

    /**
     * Logout endpoint - client-side token deletion (stateless JWT)
     * Server doesn't need to do anything as JWT is stateless
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // With JWT, logout is handled client-side by deleting the token
        // This endpoint exists for API consistency and future token blacklisting
        return ResponseEntity.ok("Logged out successfully. Please delete the token on client side.");
    }
}
