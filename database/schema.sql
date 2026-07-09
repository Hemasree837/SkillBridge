-- ==========================================================
-- SkillBridge Database Schema
-- Note: You don't have to run this manually — Spring Boot's
-- spring.jpa.hibernate.ddl-auto=update setting will auto-create
-- these tables the first time you run the backend.
-- This file is provided so you can see/inspect the schema, or
-- create it manually if you prefer.
-- ==========================================================

CREATE DATABASE IF NOT EXISTS skillbridge_db;
USE skillbridge_db;

-- ----------------------------------------------------------
-- USERS table
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    college VARCHAR(255),
    department VARCHAR(255),
    year INT,
    bio VARCHAR(500)
);

-- ----------------------------------------------------------
-- SKILLS table
-- type: TEACH or LEARN
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    skill_name VARCHAR(255) NOT NULL,
    level VARCHAR(50),
    type VARCHAR(20) NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_skill_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ----------------------------------------------------------
-- SWAP_REQUESTS table
-- status: PENDING, ACCEPTED, REJECTED, COMPLETED
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS swap_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    message VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_date DATETIME,
    CONSTRAINT fk_request_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_request_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);
