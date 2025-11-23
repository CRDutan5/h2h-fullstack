package com.example.h2h_project.service;

import com.example.h2h_project.model.Coach;
import com.example.h2h_project.repository.CoachRepository;
import org.springframework.stereotype.Service;

@Service
public class CoachService {

    private final CoachRepository coachRepository;

    public CoachService(CoachRepository coachRepository) {
        this.coachRepository = coachRepository;
    }

    public Coach createCoach(Coach coach) {
        Long coachId = coachRepository.createCoach(coach);
        coach.setId(coachId);
        return coach;
    }
}
