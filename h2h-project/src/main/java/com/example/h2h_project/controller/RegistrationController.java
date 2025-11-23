package com.example.h2h_project.controller;

import com.example.h2h_project.requests.CoachRegistrationRequest;
import com.example.h2h_project.requests.PlayerRegistrationRequest;
import com.example.h2h_project.requests.RefereeRegistrationRequest;
import com.example.h2h_project.model.Coach;
import com.example.h2h_project.model.Player;
import com.example.h2h_project.model.Referee;
import com.example.h2h_project.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/player")
    public ResponseEntity<Player> registerPlayer(@Valid @RequestBody PlayerRegistrationRequest playerRegistrationRequest) {
        Player player = registrationService.registerPlayer(playerRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(player);
    }

    @PostMapping("/referee")
    public ResponseEntity<Referee> registerReferee(@Valid @RequestBody RefereeRegistrationRequest refereeRegistrationRequest) {
        Referee referee = registrationService.registerReferee(refereeRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(referee);
    }

    @PostMapping("/coach")
    public ResponseEntity<Coach> registerCoach(@Valid @RequestBody CoachRegistrationRequest coachRegistrationRequest) {
        Coach coach = registrationService.registerCoach(coachRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(coach);
    }

}
