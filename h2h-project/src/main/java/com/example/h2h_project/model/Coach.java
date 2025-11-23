package com.example.h2h_project.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Coach {

    private Long id;

    @NotNull
    private Long userId;

    private Long teamId;

    private String certificationLevel;

    private int yearsExperience;

}
