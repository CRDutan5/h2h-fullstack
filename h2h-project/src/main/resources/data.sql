-- Mock Data for H2H Backend
-- This file is automatically loaded by Spring Boot on startup

-- Insert Users (passwords are 'password123' encrypted with BCrypt)
-- BCrypt hash for 'password123': $2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC
INSERT INTO users (email, password, first_name, last_name, zip_code, role, created_at, updated_at) VALUES
('john.striker@example.com', '$2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC', 'John', 'Striker', '12345', 'PLAYER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('mike.keeper@example.com', '$2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC', 'Mike', 'Keeper', '12345', 'PLAYER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sarah.defender@example.com', '$2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC', 'Sarah', 'Defender', '12345', 'PLAYER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('alex.mid@example.com', '$2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC', 'Alex', 'Midfielder', '12345', 'PLAYER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('emma.forward@example.com', '$2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC', 'Emma', 'Forward', '67890', 'PLAYER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('coach.jones@example.com', '$2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC', 'Tom', 'Jones', '12345', 'COACH', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ref.smith@example.com', '$2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC', 'Jane', 'Smith', '12345', 'REFEREE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('lily.goalie@example.com', '$2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC', 'Lily', 'Guardian', '67890', 'PLAYER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('chris.back@example.com', '$2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC', 'Chris', 'Backfield', '67890', 'PLAYER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('coach.williams@example.com', '$2a$10$XQ3Zh7jxGxYPVp5kqjPEYuP.yH5LlPT5XQJJQ9lZ8KqB5R3fO/XKC', 'Lisa', 'Williams', '67890', 'COACH', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Players (without team assignments initially)
INSERT INTO players (user_id, position, team_id) VALUES
(1, 'FORWARD', NULL),      -- John Striker
(2, 'GOALKEEPER', NULL),   -- Mike Keeper
(3, 'DEFENDER', NULL),     -- Sarah Defender
(4, 'MIDFIELDER', NULL),   -- Alex Midfielder
(5, 'FORWARD', NULL),      -- Emma Forward
(8, 'GOALKEEPER', NULL),   -- Lily Guardian
(9, 'DEFENDER', NULL);     -- Chris Backfield

-- Insert Teams (using player IDs as captains)
INSERT INTO teams (name, captain_id, zip_code, stadium, description, logo_url, home_color, away_color, wins, draws, losses, max_roster_size, current_roster_size, created_at, updated_at) VALUES
('Thunder FC', 1, '12345', 'Thunder Stadium', 'A competitive local team focused on teamwork and skill', 'https://example.com/thunder-logo.png', 'Blue', 'White', 3, 1, 0, 25, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Lightning United', 5, '67890', 'Lightning Arena', 'Fast-paced attacking football with young talent', 'https://example.com/lightning-logo.png', 'Yellow', 'Black', 2, 2, 1, 20, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Update players to assign them to teams
UPDATE players SET team_id = 1 WHERE id IN (1, 2, 3, 4);  -- Thunder FC roster
UPDATE players SET team_id = 2 WHERE id IN (5, 8, 9);     -- Lightning United roster

-- Insert Coaches
INSERT INTO coaches (user_id, team_id, certification_level, years_experience) VALUES
(6, 1, 'UEFA A License', 12),    -- Coach Jones for Thunder FC
(10, 2, 'UEFA B License', 8);    -- Coach Williams for Lightning United

-- Insert Referees
INSERT INTO referees (user_id, certification_level, years_experience) VALUES
(7, 'FIFA Level 3', 10);         -- Ref Smith
