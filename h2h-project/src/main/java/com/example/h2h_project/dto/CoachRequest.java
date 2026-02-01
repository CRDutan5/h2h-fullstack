package com.example.h2h_project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CoachRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Certification level is required")
    private String certificationLevel;

    @Min(value = 0, message = "Years of experience cannot be negative")
    private Integer yearsExperience = 0;

    private String specialization;

    private String bio;
}
