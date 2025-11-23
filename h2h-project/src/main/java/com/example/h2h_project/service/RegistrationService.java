package com.example.h2h_project.service;

import com.example.h2h_project.requests.CoachRegistrationRequest;
import com.example.h2h_project.requests.PlayerRegistrationRequest;
import com.example.h2h_project.requests.RefereeRegistrationRequest;
import com.example.h2h_project.model.Coach;
import com.example.h2h_project.model.Player;
import com.example.h2h_project.model.Referee;
import com.example.h2h_project.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserService userService;
    private final PlayerService playerService;
    private final RefereeService refereeService;
    private final CoachService coachService;

    public RegistrationService(UserService userService, PlayerService playerService,
                                RefereeService refereeService, CoachService coachService) {
        this.userService = userService;
        this.playerService = playerService;
        this.refereeService = refereeService;
        this.coachService = coachService;
    }

    @Transactional
    public Player registerPlayer(PlayerRegistrationRequest playerRegistrationRequest) {
        User user = userService.registerNewUser(playerRegistrationRequest.toUser());

        Player player = playerRegistrationRequest.toPlayer(user.getId());

        return playerService.createPlayer(player);
    }

    @Transactional
    public Referee registerReferee(RefereeRegistrationRequest refereeRegistrationRequest) {
        User user = userService.registerNewUser(refereeRegistrationRequest.toUser());

        Referee referee = refereeRegistrationRequest.toReferee(user.getId());

        return refereeService.createReferee(referee);
    }

    @Transactional
    public Coach registerCoach(CoachRegistrationRequest coachRegistrationRequest) {
        User user = userService.registerNewUser(coachRegistrationRequest.toUser());

        Coach coach = coachRegistrationRequest.toCoach(user.getId());

        return coachService.createCoach(coach);
    }

}
