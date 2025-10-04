package com.example.h2h_project.service;

import com.example.h2h_project.dto.PlayerDTO;
import com.example.h2h_project.dto.TeamDetailsWithRosterDTO;
import com.example.h2h_project.dto.TeamDTO;
import com.example.h2h_project.repository.PlayerRepository;
import com.example.h2h_project.requests.TeamCreateRequest;
import com.example.h2h_project.model.Team;
import com.example.h2h_project.repository.TeamRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

}

