package com.example.h2h_project.service;

import com.example.h2h_project.model.User;
import com.example.h2h_project.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        Long userId = userRepository.createUser(user);

        if (userId == null) {
            throw new RuntimeException("Failed to register user");
        }

        return user;
    }
}
