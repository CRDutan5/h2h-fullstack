package com.example.h2h_project.requests;

import com.example.h2h_project.model.Referee;
import com.example.h2h_project.model.User;
import com.example.h2h_project.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefereeRegistrationRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String firstName;
    private String lastName;
    private String zipCode;

    private String certificationLevel;
    private int yearsExperience;

    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setZipCode(this.zipCode);
        user.setRole(UserRole.REFEREE);
        user.setCreatedAt(java.time.LocalDateTime.now());
        user.setUpdatedAt(java.time.LocalDateTime.now());
        return user;
    }

    public Referee toReferee(Long userId) {
        Referee referee = new Referee();
        referee.setUserId(userId);
        referee.setCertificationLevel(this.certificationLevel);
        referee.setYearsExperience(this.yearsExperience);
        return referee;
    }
}
