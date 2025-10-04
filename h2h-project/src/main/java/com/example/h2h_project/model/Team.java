package com.example.h2h_project.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Team {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long captainId;

    @NotNull
    private String zipCode;

    @NotNull
    private String stadium;

    private String description;

    private String logoUrl;

    private String homeColor;

    private String awayColor;

    private int wins;

    private int draws;

    private int losses;

    private int maxRosterSize;

    private int currentRosterSize;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;



}
