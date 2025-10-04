package com.example.h2h_project.dto;

import lombok.Data;

@Data
public class PlayerDTO {
    private Long playerId;
    private Long userId;
    private int position;
    private String firstName;
    private String lastName;
}
