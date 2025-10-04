package com.example.h2h_project.mapper;

import com.example.h2h_project.dto.TeamDTO;
import com.example.h2h_project.model.Team;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;

@Component
public class TeamMapper {

    public TeamDTO toTeamDTO(Team team) {
        TeamDTO dto = new TeamDTO();
        BeanUtils.copyProperties(team, dto);
        return dto;
    }
}
