package com.example.h2h_project.controller;

import com.example.h2h_project.dto.LoginRequest;
import com.example.h2h_project.dto.LoginResponse;
import com.example.h2h_project.dto.RegisterRequest;
import com.example.h2h_project.dto.RegisterResponse;
import com.example.h2h_project.model.User;
import com.example.h2h_project.repository.UserRepository;
import com.example.h2h_project.security.JwtUtil;
import com.example.h2h_project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserDetailsService userDetailsService,
                          JwtUtil jwtUtil,
                          UserRepository userRepository,
                          UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            // Create user object
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(registerRequest.getPassword());
            user.setFirstName(registerRequest.getFirstName());
            user.setLastName(registerRequest.getLastName());

            // Register the user
            User createdUser = userService.registerNewUser(user);

            // Return response
            RegisterResponse response = new RegisterResponse(
                    createdUser.getId(),
                    createdUser.getEmail(),
                    createdUser.getFirstName(),
                    createdUser.getLastName(),
                    "User registered successfully"
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during registration");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            // Load user details
            final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

            // Generate JWT token
            final String jwt = jwtUtil.generateToken(userDetails);

            // Fetch user info for response
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Return response with token and user info
            LoginResponse response = new LoginResponse(
                    jwt,
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName()
            );

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during authentication");
        }
    }
}
