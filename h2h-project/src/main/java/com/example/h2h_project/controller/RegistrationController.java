package com.example.h2h_project.controller;

import com.example.h2h_project.requests.PlayerRegistrationRequest;
import com.example.h2h_project.model.Player;
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

}
