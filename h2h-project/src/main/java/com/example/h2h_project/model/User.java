package com.example.h2h_project.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {

    private Long id;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // No-args constructor
    public User() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    // Convenience constructor
    public User(String email, String password, String firstName, String lastName) {
        this();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
