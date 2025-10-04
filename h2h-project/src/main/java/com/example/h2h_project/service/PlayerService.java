package com.example.h2h_project.service;

import com.example.h2h_project.dto.PlayerDTO;
import com.example.h2h_project.model.Player;
import com.example.h2h_project.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(Player player) {
        Long playerId = playerRepository.createPlayer(player);
        player.setId(playerId);
        return player;
    }

    

}
