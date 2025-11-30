package com.example.h2h_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
}
