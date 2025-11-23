package com.example.h2h_project.requests;

import com.example.h2h_project.model.Player;
import com.example.h2h_project.model.Position;
import com.example.h2h_project.model.User;
import com.example.h2h_project.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlayerRegistrationRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String firstName;
    private String lastName;
    private String zipCode;

    @NotNull
    private Position position;
    private Long teamId;

    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setZipCode(this.zipCode);
        user.setRole(UserRole.PLAYER);
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        return user;
    }

    public Player toPlayer(Long userId) {
        Player player = new Player();
        player.setPosition(this.position);
        player.setTeamId(this.teamId);
        player.setUserId(userId);
        return player;
    }
}
