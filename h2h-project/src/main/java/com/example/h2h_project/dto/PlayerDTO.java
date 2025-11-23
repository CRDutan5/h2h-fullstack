package com.example.h2h_project.dto;

import com.example.h2h_project.model.Position;
import lombok.Data;

@Data
public class PlayerDTO {
    private Long playerId;
    private Long userId;
    private Position position;
    private String firstName;
    private String lastName;
}
