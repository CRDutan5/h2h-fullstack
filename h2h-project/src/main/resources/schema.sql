-- Users table - Core authentication and user information
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Players table - References users
CREATE TABLE IF NOT EXISTS players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    position VARCHAR(50) NOT NULL,
    skill_level VARCHAR(20) NOT NULL,
    years_experience INT DEFAULT 0,
    bio TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_player_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Coaches table - References users
CREATE TABLE IF NOT EXISTS coaches (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    certification_level VARCHAR(50) NOT NULL,
    years_experience INT DEFAULT 0,
    specialization VARCHAR(100),
    bio TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_coach_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Referees table - References users
CREATE TABLE IF NOT EXISTS referees (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    certification_level VARCHAR(50) NOT NULL,
    years_experience INT DEFAULT 0,
    bio TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_referee_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
