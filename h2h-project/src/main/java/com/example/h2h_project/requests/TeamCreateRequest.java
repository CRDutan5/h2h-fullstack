package com.example.h2h_project.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TeamCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long captainId;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String stadium;

    private String description;
    private String logoUrl;
    private String homeColor;
    private String awayColor;
    private int maxRosterSize;
}

