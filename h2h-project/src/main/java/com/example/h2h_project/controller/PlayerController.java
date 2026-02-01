package com.example.h2h_project.controller;

import com.example.h2h_project.dto.PlayerRequest;
import com.example.h2h_project.model.Player;
import com.example.h2h_project.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<?> createPlayer(@Valid @RequestBody PlayerRequest request) {
        try {
            Player player = new Player();
            player.setUserId(request.getUserId());
            player.setPosition(request.getPosition());
            player.setSkillLevel(request.getSkillLevel());
            player.setYearsExperience(request.getYearsExperience());
            player.setBio(request.getBio());

            Player created = playerService.create(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlayer(@PathVariable Long id) {
        return playerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getPlayerByUserId(@PathVariable Long userId) {
        return playerService.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        return ResponseEntity.ok(playerService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlayer(@PathVariable Long id, @Valid @RequestBody PlayerRequest request) {
        try {
            Player player = new Player();
            player.setPosition(request.getPosition());
            player.setSkillLevel(request.getSkillLevel());
            player.setYearsExperience(request.getYearsExperience());
            player.setBio(request.getBio());

            Player updated = playerService.update(id, player);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlayer(@PathVariable Long id) {
        try {
            playerService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
