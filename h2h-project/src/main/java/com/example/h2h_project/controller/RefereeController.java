package com.example.h2h_project.controller;

import com.example.h2h_project.dto.RefereeRequest;
import com.example.h2h_project.model.Referee;
import com.example.h2h_project.service.RefereeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/referees")
public class RefereeController {

    private final RefereeService refereeService;

    public RefereeController(RefereeService refereeService) {
        this.refereeService = refereeService;
    }

    @PostMapping
    public ResponseEntity<?> createReferee(@Valid @RequestBody RefereeRequest request) {
        try {
            Referee referee = new Referee();
            referee.setUserId(request.getUserId());
            referee.setCertificationLevel(request.getCertificationLevel());
            referee.setYearsExperience(request.getYearsExperience());
            referee.setBio(request.getBio());

            Referee created = refereeService.create(referee);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReferee(@PathVariable Long id) {
        return refereeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getRefereeByUserId(@PathVariable Long userId) {
        return refereeService.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Referee>> getAllReferees() {
        return ResponseEntity.ok(refereeService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReferee(@PathVariable Long id, @Valid @RequestBody RefereeRequest request) {
        try {
            Referee referee = new Referee();
            referee.setCertificationLevel(request.getCertificationLevel());
            referee.setYearsExperience(request.getYearsExperience());
            referee.setBio(request.getBio());

            Referee updated = refereeService.update(id, referee);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReferee(@PathVariable Long id) {
        try {
            refereeService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
