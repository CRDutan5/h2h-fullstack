package com.example.h2h_project.service;

import com.example.h2h_project.dto.PlayerDTO;
import com.example.h2h_project.dto.TeamDetailsWithRosterDTO;
import com.example.h2h_project.dto.TeamDTO;
import com.example.h2h_project.model.Player;
import com.example.h2h_project.repository.PlayerRepository;
import com.example.h2h_project.requests.TeamCreateRequest;
import com.example.h2h_project.model.Team;
import com.example.h2h_project.repository.TeamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public Team createTeam(TeamCreateRequest request) {
        Team team = new Team();
        team.setName(request.getName());
        team.setCaptainId(request.getCaptainId());
        team.setZipCode(request.getZipCode());
        team.setStadium(request.getStadium());
        team.setDescription(request.getDescription());
        team.setLogoUrl(request.getLogoUrl());
        team.setHomeColor(request.getHomeColor());
        team.setAwayColor(request.getAwayColor());
        team.setMaxRosterSize(request.getMaxRosterSize());
        team.setCurrentRosterSize(1); // captain counts as 1
        team.setWins(0);
        team.setDraws(0);
        team.setLosses(0);
        team.setCreatedAt(LocalDateTime.now());
        team.setUpdatedAt(LocalDateTime.now());

        Long teamId = teamRepository.createTeam(team);
        boolean teamIdUpdatedForPlayer = playerRepository.updatePlayerTeamId(request.getCaptainId(), teamId);

        if (!teamIdUpdatedForPlayer) {
            throw new RuntimeException("Failed to update player's team ID");
        }

        team.setId(teamId);

        return team;
    }

    public TeamDetailsWithRosterDTO getTeamDetailsWithPlayers(Long teamId) {
        Team team = teamRepository.getTeamById(teamId);
        if (team == null) {
            throw new RuntimeException("Team not found");
        }

        TeamDTO teamDTO = new TeamDTO();
        BeanUtils.copyProperties(team, teamDTO);

        List<PlayerDTO> players = playerRepository.getPlayersByTeamId(teamId);

        TeamDetailsWithRosterDTO teamDetailsWithRosterDTO = new TeamDetailsWithRosterDTO();
        teamDetailsWithRosterDTO.setTeam(teamDTO);
        teamDetailsWithRosterDTO.setPlayers(players);

        return teamDetailsWithRosterDTO;
    }

    /**
     * Adds a player to a team's roster.
     * Validates that the player exists, is not already on a team, and that the roster is not full.
     */
    @Transactional
    public void addPlayerToTeam(Long playerId, Long teamId) {
        // Get the team
        Team team = teamRepository.getTeamById(teamId);
        if (team == null) {
            throw new RuntimeException("Team not found with id: " + teamId);
        }

        // Get the player
        Player player = playerRepository.getPlayerById(playerId);
        if (player == null) {
            throw new RuntimeException("Player not found with id: " + playerId);
        }

        // Check if player is already on a team
        if (player.getTeamId() != null) {
            throw new RuntimeException("Player is already on a team. Players can only be on one team at a time.");
        }

        // Check if roster is full
        int currentRosterCount = playerRepository.countPlayersByTeamId(teamId);
        if (currentRosterCount >= team.getMaxRosterSize()) {
            throw new RuntimeException("Team roster is full. Max size: " + team.getMaxRosterSize());
        }

        // Add player to team
        boolean updated = playerRepository.updatePlayerTeamId(playerId, teamId);
        if (!updated) {
            throw new RuntimeException("Failed to add player to team");
        }

        // Update roster size
        teamRepository.updateRosterSize(teamId, currentRosterCount + 1);
    }

    /**
     * Removes a player from a team's roster.
     * Validates that the player is on the team and is not the captain.
     */
    @Transactional
    public void removePlayerFromTeam(Long playerId, Long teamId) {
        // Get the team
        Team team = teamRepository.getTeamById(teamId);
        if (team == null) {
            throw new RuntimeException("Team not found with id: " + teamId);
        }

        // Get the player
        Player player = playerRepository.getPlayerById(playerId);
        if (player == null) {
            throw new RuntimeException("Player not found with id: " + playerId);
        }

        // Check if player is on this team
        if (player.getTeamId() == null || !player.getTeamId().equals(teamId)) {
            throw new RuntimeException("Player is not on this team");
        }

        // Check if player is the captain
        if (team.getCaptainId().equals(playerId)) {
            throw new RuntimeException("Cannot remove the team captain. Assign a new captain first.");
        }

        // Remove player from team
        boolean updated = playerRepository.updatePlayerTeamId(playerId, null);
        if (!updated) {
            throw new RuntimeException("Failed to remove player from team");
        }

        // Update roster size
        int currentRosterCount = playerRepository.countPlayersByTeamId(teamId);
        teamRepository.updateRosterSize(teamId, currentRosterCount);
    }

}

