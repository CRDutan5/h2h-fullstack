-- Sample test users with BCrypt hashed passwords
-- All passwords are "password123" for testing purposes
-- BCrypt hash: $2a$10$rBV2xJsHGbYBYJDgGvdnPOzXFh9s8BXeFHZfN3YlVBv8dKYWBKNwe

-- Insert Users
INSERT INTO users (email, password, first_name, last_name, created_at, updated_at) VALUES
('john.player@example.com', '$2a$10$rBV2xJsHGbYBYJDgGvdnPOzXFh9s8BXeFHZfN3YlVBv8dKYWBKNwe', 'John', 'Player', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('jane.coach@example.com', '$2a$10$rBV2xJsHGbYBYJDgGvdnPOzXFh9s8BXeFHZfN3YlVBv8dKYWBKNwe', 'Jane', 'Coach', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('mike.referee@example.com', '$2a$10$rBV2xJsHGbYBYJDgGvdnPOzXFh9s8BXeFHZfN3YlVBv8dKYWBKNwe', 'Mike', 'Referee', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('sarah.player@example.com', '$2a$10$rBV2xJsHGbYBYJDgGvdnPOzXFh9s8BXeFHZfN3YlVBv8dKYWBKNwe', 'Sarah', 'Wilson', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('tom.coach@example.com', '$2a$10$rBV2xJsHGbYBYJDgGvdnPOzXFh9s8BXeFHZfN3YlVBv8dKYWBKNwe', 'Tom', 'Anderson', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insert Players
INSERT INTO players (user_id, position, skill_level, years_experience, bio) VALUES
(1, 'Forward', 'Advanced', 5, 'Experienced forward with strong scoring abilities'),
(4, 'Midfielder', 'Intermediate', 3, 'Versatile midfielder, great ball control');

-- Insert Coaches
INSERT INTO coaches (user_id, certification_level, years_experience, specialization, bio) VALUES
(2, 'Level 3 UEFA', 8, 'Youth Development', 'Passionate about developing young talent'),
(5, 'Level 2 USSF', 4, 'Tactical Training', 'Focus on tactical awareness and strategy');

-- Insert Referees
INSERT INTO referees (user_id, certification_level, years_experience, bio) VALUES
(3, 'FIFA Certified', 10, 'International referee with experience in major tournaments');
