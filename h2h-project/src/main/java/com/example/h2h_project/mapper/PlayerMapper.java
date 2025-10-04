package com.example.h2h_project.mapper;

import com.example.h2h_project.dto.PlayerDTO;
import com.example.h2h_project.model.Player;
import com.example.h2h_project.model.User;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public PlayerDTO toPlayerDTO(Player player, User user) {
        PlayerDTO dto = new PlayerDTO();
        dto.setPlayerId(player.getId());
        dto.setUserId(user.getId());
        dto.setPosition(player.getPosition());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        return dto;
    }

}
