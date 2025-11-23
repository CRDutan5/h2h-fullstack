package com.example.h2h_project.controller;

import com.example.h2h_project.dto.TeamDetailsWithRosterDTO;
import com.example.h2h_project.requests.TeamCreateRequest;
import com.example.h2h_project.model.Team;
import com.example.h2h_project.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@Valid @RequestBody TeamCreateRequest request) {
        Team createdTeam = teamService.createTeam(request);
        return ResponseEntity.status(201).body(createdTeam);
    }

    @GetMapping("/{id}/with-players")
    public ResponseEntity<TeamDetailsWithRosterDTO> getTeamDetailsWithPlayers(@PathVariable Long id) {
        TeamDetailsWithRosterDTO roster = teamService.getTeamDetailsWithPlayers(id);
        return ResponseEntity.status(200).body(roster);
    }

    @PostMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<String> addPlayerToTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        teamService.addPlayerToTeam(playerId, teamId);
        return ResponseEntity.status(200).body("Player added to team successfully");
    }

    @DeleteMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<String> removePlayerFromTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        teamService.removePlayerFromTeam(playerId, teamId);
        return ResponseEntity.status(200).body("Player removed from team successfully");
    }

}
