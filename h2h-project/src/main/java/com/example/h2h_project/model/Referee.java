package com.example.h2h_project.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Referee {

    private Long id;

    @NotNull
    private Long userId;

    private String certificationLevel;

    private int yearsExperience;

}
