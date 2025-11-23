package com.example.h2h_project.model;

/**
 * Enum representing the different positions a player can have on a team.
 */
public enum Position {
    GOALKEEPER(1, "Goalkeeper"),
    DEFENDER(2, "Defender"),
    MIDFIELDER(3, "Midfielder"),
    FORWARD(4, "Forward");

    private final int code;
    private final String displayName;

    Position(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public int getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Converts a position code (1-4) to a Position enum value.
     *
     * @param code the position code
     * @return the corresponding Position
     * @throws IllegalArgumentException if the code is invalid
     */
    public static Position fromCode(int code) {
        for (Position position : Position.values()) {
            if (position.code == code) {
                return position;
            }
        }
        throw new IllegalArgumentException("Invalid position code: " + code);
    }

    /**
     * Converts a string to a Position enum value.
     * Case-insensitive.
     *
     * @param position the position string
     * @return the corresponding Position
     * @throws IllegalArgumentException if the position is invalid
     */
    public static Position fromString(String position) {
        if (position == null) {
            throw new IllegalArgumentException("Position cannot be null");
        }
        return Position.valueOf(position.toUpperCase());
    }
}
