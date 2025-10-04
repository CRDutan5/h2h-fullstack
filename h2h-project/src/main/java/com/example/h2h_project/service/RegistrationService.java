package com.example.h2h_project.service;

import com.example.h2h_project.requests.PlayerRegistrationRequest;
import com.example.h2h_project.model.Player;
import com.example.h2h_project.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserService userService;
    private final PlayerService playerService;

    public RegistrationService(UserService userService, PlayerService playerService) {
        this.userService = userService;
        this.playerService = playerService;
    }

    @Transactional
    public Player registerPlayer(PlayerRegistrationRequest playerRegistrationRequest) {
        User user = userService.registerNewUser(playerRegistrationRequest.toUser());

        Player player = playerRegistrationRequest.toPlayer(user.getId());

        return playerService.createPlayer(player);
    }

}
