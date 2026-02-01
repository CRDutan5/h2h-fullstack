package com.example.h2h_project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlayerRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Position is required")
    private String position;

    @NotBlank(message = "Skill level is required")
    private String skillLevel;

    @Min(value = 0, message = "Years of experience cannot be negative")
    private Integer yearsExperience = 0;

    private String bio;
}
