package com.example.h2h_project.dto;

import lombok.Data;

// TeamDTO.java
@Data
public class TeamDTO {
    private Long id;
    private String name;
    private Long captainId;
    private String stadium;
    private String logoUrl;
    private String homeColor;
    private String awayColor;
    private int wins;
    private int draws;
    private int losses;
}

