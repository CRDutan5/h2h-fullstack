package com.example.h2h_project.service;

import com.example.h2h_project.model.Coach;
import com.example.h2h_project.repository.CoachRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachService {

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public Coach create(Coach coach) {
        // Check if user already has a coach profile
        Optional<Coach> existing = coachRepository.findByUserId(coach.getUserId());
        if (existing.isPresent()) {
            throw new RuntimeException("User already has a coach profile");
        }

        Long coachId = coachRepository.create(coach);
        if (coachId == null) {
            throw new RuntimeException("Failed to create coach profile");
        }

        coach.setId(coachId);
        return coachRepository.findById(coachId)
                .orElseThrow(() -> new RuntimeException("Coach created but not found"));
    }

    public Optional<Coach> findById(Long id) {
        return coachRepository.findById(id);
    }

    public Optional<Coach> findByUserId(Long userId) {
        return coachRepository.findByUserId(userId);
    }

    public List<Coach> findAll() {
        return coachRepository.findAll();
    }

    public Coach update(Long id, Coach coach) {
        Coach existing = coachRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coach not found"));

        existing.setCertificationLevel(coach.getCertificationLevel());
        existing.setYearsExperience(coach.getYearsExperience());
        existing.setSpecialization(coach.getSpecialization());
        existing.setBio(coach.getBio());

        coachRepository.update(existing);
        return coachRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coach updated but not found"));
    }

    public void delete(Long id) {
        if (!coachRepository.findById(id).isPresent()) {
            throw new RuntimeException("Coach not found");
        }
        coachRepository.delete(id);
    }
}
