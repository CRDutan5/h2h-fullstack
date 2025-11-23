package com.example.h2h_project.controller;

import com.example.h2h_project.dto.AuthResponse;
import com.example.h2h_project.requests.CoachRegistrationRequest;
import com.example.h2h_project.requests.PlayerRegistrationRequest;
import com.example.h2h_project.requests.RefereeRegistrationRequest;
import com.example.h2h_project.model.Coach;
import com.example.h2h_project.model.Player;
import com.example.h2h_project.model.Referee;
import com.example.h2h_project.model.User;
import com.example.h2h_project.repository.UserRepository;
import com.example.h2h_project.security.JwtUtil;
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
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public RegistrationController(RegistrationService registrationService,
                                   UserRepository userRepository,
                                   JwtUtil jwtUtil) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/player")
    public ResponseEntity<AuthResponse> registerPlayer(@Valid @RequestBody PlayerRegistrationRequest playerRegistrationRequest) {
        Player player = registrationService.registerPlayer(playerRegistrationRequest);

        // Get the user to generate token
        User user = userRepository.findByEmail(playerRegistrationRequest.getEmail()).orElseThrow();

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        // Create auth response (auto-login after registration)
        AuthResponse authResponse = new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getFirstName(),
                user.getLastName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/referee")
    public ResponseEntity<AuthResponse> registerReferee(@Valid @RequestBody RefereeRegistrationRequest refereeRegistrationRequest) {
        Referee referee = registrationService.registerReferee(refereeRegistrationRequest);

        // Get the user to generate token
        User user = userRepository.findByEmail(refereeRegistrationRequest.getEmail()).orElseThrow();

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        // Create auth response (auto-login after registration)
        AuthResponse authResponse = new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getFirstName(),
                user.getLastName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/coach")
    public ResponseEntity<AuthResponse> registerCoach(@Valid @RequestBody CoachRegistrationRequest coachRegistrationRequest) {
        Coach coach = registrationService.registerCoach(coachRegistrationRequest);

        // Get the user to generate token
        User user = userRepository.findByEmail(coachRegistrationRequest.getEmail()).orElseThrow();

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());

        // Create auth response (auto-login after registration)
        AuthResponse authResponse = new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole().name(),
                user.getFirstName(),
                user.getLastName()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

}
