package com.example.h2h_project.model;

/**
 * Enum representing the different roles a user can have in the system.
 * This allows for flexible role management and easy extension for future roles.
 */
public enum UserRole {
    PLAYER,
    REFEREE,
    COACH;

    /**
     * Converts a string to a UserRole enum value.
     * Case-insensitive.
     *
     * @param role the role string
     * @return the corresponding UserRole
     * @throws IllegalArgumentException if the role is invalid
     */
    public static UserRole fromString(String role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        return UserRole.valueOf(role.toUpperCase());
    }
}
