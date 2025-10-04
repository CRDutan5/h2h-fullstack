package com.example.h2h_project.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDetailsWithRosterDTO {
    private TeamDTO team;
    private List<PlayerDTO> players;
}
