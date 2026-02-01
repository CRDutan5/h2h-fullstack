package com.example.h2h_project.controller;

import com.example.h2h_project.dto.CoachRequest;
import com.example.h2h_project.model.Coach;
import com.example.h2h_project.service.CoachService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coaches")
public class CoachController {

    private final CoachService coachService;

    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @PostMapping
    public ResponseEntity<?> createCoach(@Valid @RequestBody CoachRequest request) {
        try {
            Coach coach = new Coach();
            coach.setUserId(request.getUserId());
            coach.setCertificationLevel(request.getCertificationLevel());
            coach.setYearsExperience(request.getYearsExperience());
            coach.setSpecialization(request.getSpecialization());
            coach.setBio(request.getBio());

            Coach created = coachService.create(coach);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCoach(@PathVariable Long id) {
        return coachService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getCoachByUserId(@PathVariable Long userId) {
        return coachService.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches() {
        return ResponseEntity.ok(coachService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCoach(@PathVariable Long id, @Valid @RequestBody CoachRequest request) {
        try {
            Coach coach = new Coach();
            coach.setCertificationLevel(request.getCertificationLevel());
            coach.setYearsExperience(request.getYearsExperience());
            coach.setSpecialization(request.getSpecialization());
            coach.setBio(request.getBio());

            Coach updated = coachService.update(id, coach);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoach(@PathVariable Long id) {
        try {
            coachService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
