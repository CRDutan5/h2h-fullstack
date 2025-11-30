-- Sample test users with BCrypt hashed passwords
-- All passwords are "password123" for testing purposes
-- BCrypt hash: $2a$10$rBV2xJsHGbYBYJDgGvdnPOzXFh9s8BXeFHZfN3YlVBv8dKYWBKNwe

INSERT INTO users (email, password, first_name, last_name, zip_code, created_at, updated_at) VALUES
('john.doe@example.com', '$2a$10$rBV2xJsHGbYBYJDgGvdnPOzXFh9s8BXeFHZfN3YlVBv8dKYWBKNwe', 'John', 'Doe', '12345', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('jane.smith@example.com', '$2a$10$rBV2xJsHGbYBYJDgGvdnPOzXFh9s8BXeFHZfN3YlVBv8dKYWBKNwe', 'Jane', 'Smith', '67890', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('test.user@example.com', '$2a$10$rBV2xJsHGbYBYJDgGvdnPOzXFh9s8BXeFHZfN3YlVBv8dKYWBKNwe', 'Test', 'User', '54321', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
