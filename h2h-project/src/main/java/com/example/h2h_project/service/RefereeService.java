package com.example.h2h_project.service;

import com.example.h2h_project.model.Referee;
import com.example.h2h_project.repository.RefereeRepository;
import org.springframework.stereotype.Service;

@Service
public class RefereeService {

    private final RefereeRepository refereeRepository;

    public RefereeService(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }

    public Referee createReferee(Referee referee) {
        Long refereeId = refereeRepository.createReferee(referee);
        referee.setId(refereeId);
        return referee;
    }
}
