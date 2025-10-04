-- Create users table first (no dependencies)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Create teams table WITHOUT the captain foreign key constraint
CREATE TABLE IF NOT EXISTS teams (
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
CREATE TABLE IF NOT EXISTS players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    team_id BIGINT,
    user_id BIGINT NOT NULL,
    position INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_team FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE SET NULL
);

-- Now add the captain foreign key constraint to teams
ALTER TABLE teams 
ADD CONSTRAINT fk_captain 
FOREIGN KEY (captain_id) REFERENCES players(id) ON DELETE RESTRICT;