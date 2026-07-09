-- ==========================================================
-- SkillBridge Sample Data
-- Run this AFTER the backend has started at least once
-- (so the tables already exist), or after running schema.sql.
-- All sample passwords are "password123" (plain text, v1 only).
-- ==========================================================

USE skillbridge_db;

INSERT INTO users (full_name, email, password, college, department, year, bio) VALUES
('Hemasree Kotika', 'hema@example.com', 'password123', 'Mother Theresa Institute of Engineering', 'CSE', 4, 'Full-stack dev who loves React and Spring Boot.'),
('Arjun Rao', 'arjun@example.com', 'password123', 'Mother Theresa Institute of Engineering', 'CSE', 3, 'Backend enthusiast learning React.'),
('Priya Sharma', 'priya@example.com', 'password123', 'JNTU Hyderabad', 'IT', 4, 'Loves teaching Python and DSA.'),
('Karthik Reddy', 'karthik@example.com', 'password123', 'Osmania University', 'ECE', 2, 'Trying to learn web development.');

INSERT INTO skills (skill_name, level, type, user_id) VALUES
('React', 'Advanced', 'TEACH', 1),
('Spring Boot', 'Intermediate', 'TEACH', 1),
('Go Lang', 'Beginner', 'LEARN', 1),
('Java', 'Advanced', 'TEACH', 2),
('React', 'Beginner', 'LEARN', 2),
('Python', 'Advanced', 'TEACH', 3),
('DSA', 'Advanced', 'TEACH', 3),
('HTML/CSS', 'Beginner', 'LEARN', 4),
('JavaScript', 'Beginner', 'LEARN', 4);

INSERT INTO swap_requests (sender_id, receiver_id, message, status, created_date) VALUES
(2, 1, 'Hi! I can teach Java, want to swap for React lessons?', 'PENDING', NOW()),
(4, 3, 'Can you help me learn Python basics?', 'ACCEPTED', NOW());
