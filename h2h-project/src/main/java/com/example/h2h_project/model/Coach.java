package com.example.h2h_project.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Coach {
    private Long id;
    private Long userId;
    private String certificationLevel;
    private Integer yearsExperience;
    private String specialization;
    private String bio;
    private LocalDateTime createdAt;

    // User information (joined from users table)
    private String email;
    private String firstName;
    private String lastName;
}
