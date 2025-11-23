-- Drop existing tables in correct order (respecting foreign key constraints)
DROP TABLE IF EXISTS coaches;
DROP TABLE IF EXISTS referees;
DROP TABLE IF EXISTS players;
DROP TABLE IF EXISTS teams;
DROP TABLE IF EXISTS users;

-- Create users table first (no dependencies)
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Create teams table WITHOUT the captain foreign key constraint
CREATE TABLE teams (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    captain_id BIGINT NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    stadium VARCHAR(255) NOT NULL,
    description TEXT,
    logo_url VARCHAR(255),
    home_color VARCHAR(50),
    away_color VARCHAR(50),
    wins INT DEFAULT 0,
    draws INT DEFAULT 0,
    losses INT DEFAULT 0,
    max_roster_size INT NOT NULL,
    current_roster_size INT DEFAULT 1,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create players table (now teams exists)
CREATE TABLE players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_id BIGINT,
    user_id BIGINT NOT NULL,
    position VARCHAR(20) NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE SET NULL
);

-- Now add the captain foreign key constraint to teams
ALTER TABLE teams
ADD CONSTRAINT fk_captain
FOREIGN KEY (captain_id) REFERENCES players(id) ON DELETE RESTRICT;

-- Create referees table
CREATE TABLE referees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    certification_level VARCHAR(50),
    years_experience INT DEFAULT 0,
    CONSTRAINT fk_referee_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create coaches table
CREATE TABLE coaches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    team_id BIGINT,
    certification_level VARCHAR(50),
    years_experience INT DEFAULT 0,
    CONSTRAINT fk_coach_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_coach_team FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE SET NULL
);