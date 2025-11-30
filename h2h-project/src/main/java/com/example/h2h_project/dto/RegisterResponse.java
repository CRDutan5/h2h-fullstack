package com.example.h2h_project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String message;
}
