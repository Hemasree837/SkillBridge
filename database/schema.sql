-- ==========================================================
-- SkillBridge Database Schema (PostgreSQL)
-- Note: You don't have to run this manually — Spring Boot's
-- spring.jpa.hibernate.ddl-auto=update setting will auto-create
-- these tables the first time you run the backend, AS LONG AS
-- the "skillbridge_db" database itself already exists (see README
-- for the one-time `createdb` / CREATE DATABASE command).
-- This file is provided so you can see/inspect the schema, or
-- create the tables manually if you prefer.
-- ==========================================================

-- ----------------------------------------------------------
-- USERS table
-- ----------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
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
    id BIGSERIAL PRIMARY KEY,
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
    id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    message VARCHAR(500),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_date TIMESTAMP,
    CONSTRAINT fk_request_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_request_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);
