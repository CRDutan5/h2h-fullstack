package com.example.h2h_project.service;

import com.example.h2h_project.model.Referee;
import com.example.h2h_project.repository.RefereeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RefereeService {

    private final RefereeRepository refereeRepository;

    public RefereeService(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }

    public Referee create(Referee referee) {
        // Check if user already has a referee profile
        Optional<Referee> existing = refereeRepository.findByUserId(referee.getUserId());
        if (existing.isPresent()) {
            throw new RuntimeException("User already has a referee profile");
        }

        Long refereeId = refereeRepository.create(referee);
        if (refereeId == null) {
            throw new RuntimeException("Failed to create referee profile");
        }

        referee.setId(refereeId);
        return refereeRepository.findById(refereeId)
                .orElseThrow(() -> new RuntimeException("Referee created but not found"));
    }

    public Optional<Referee> findById(Long id) {
        return refereeRepository.findById(id);
    }

    public Optional<Referee> findByUserId(Long userId) {
        return refereeRepository.findByUserId(userId);
    }

    public List<Referee> findAll() {
        return refereeRepository.findAll();
    }

    public Referee update(Long id, Referee referee) {
        Referee existing = refereeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Referee not found"));

        existing.setCertificationLevel(referee.getCertificationLevel());
        existing.setYearsExperience(referee.getYearsExperience());
        existing.setBio(referee.getBio());

        refereeRepository.update(existing);
        return refereeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Referee updated but not found"));
    }

    public void delete(Long id) {
        if (!refereeRepository.findById(id).isPresent()) {
            throw new RuntimeException("Referee not found");
        }
        refereeRepository.delete(id);
    }
}
