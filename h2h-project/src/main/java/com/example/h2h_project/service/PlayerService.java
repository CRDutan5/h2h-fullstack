package com.example.h2h_project.service;

import com.example.h2h_project.model.Player;
import com.example.h2h_project.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player create(Player player) {
        // Check if user already has a player profile
        Optional<Player> existing = playerRepository.findByUserId(player.getUserId());
        if (existing.isPresent()) {
            throw new RuntimeException("User already has a player profile");
        }

        Long playerId = playerRepository.create(player);
        if (playerId == null) {
            throw new RuntimeException("Failed to create player profile");
        }

        player.setId(playerId);
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player created but not found"));
    }

    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    public Optional<Player> findByUserId(Long userId) {
        return playerRepository.findByUserId(userId);
    }

    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    public Player update(Long id, Player player) {
        Player existing = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        existing.setPosition(player.getPosition());
        existing.setSkillLevel(player.getSkillLevel());
        existing.setYearsExperience(player.getYearsExperience());
        existing.setBio(player.getBio());

        playerRepository.update(existing);
        return playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player updated but not found"));
    }

    public void delete(Long id) {
        if (!playerRepository.findById(id).isPresent()) {
            throw new RuntimeException("Player not found");
        }
        playerRepository.delete(id);
    }
}
