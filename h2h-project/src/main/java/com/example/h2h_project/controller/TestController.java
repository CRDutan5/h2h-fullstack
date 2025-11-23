package com.example.h2h_project.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Test controller for generating BCrypt hashes
 * REMOVE THIS IN PRODUCTION
 */
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final BCryptPasswordEncoder passwordEncoder;

    public TestController(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Generate a BCrypt hash for a given password
     * Example: GET /api/test/hash?password=password123
     */
    @GetMapping("/hash")
    public Map<String, String> generateHash(@RequestParam String password) {
        String hash = passwordEncoder.encode(password);
        boolean matches = passwordEncoder.matches(password, hash);

        Map<String, String> response = new HashMap<>();
        response.put("password", password);
        response.put("hash", hash);
        response.put("verification", String.valueOf(matches));

        return response;
    }
}
