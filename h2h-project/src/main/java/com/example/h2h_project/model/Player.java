package com.example.h2h_project.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Player {

    private Long id;

    private Long teamId;

    @NotNull
    private Long userId;

    @NotNull
    private int position;

}
