package com.example.h2h_project.requests;

import com.example.h2h_project.model.Coach;
import com.example.h2h_project.model.User;
import com.example.h2h_project.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CoachRegistrationRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String firstName;
    private String lastName;
    private String zipCode;

    private Long teamId;
    private String certificationLevel;
    private int yearsExperience;

    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setZipCode(this.zipCode);
        user.setRole(UserRole.COACH);
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        return user;
    }

    public Coach toCoach(Long userId) {
        Coach coach = new Coach();
        coach.setUserId(userId);
        coach.setTeamId(this.teamId);
        coach.setCertificationLevel(this.certificationLevel);
        coach.setYearsExperience(this.yearsExperience);
        return coach;
    }
}
