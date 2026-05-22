DROP DATABASE IF EXISTS Tunix;
CREATE DATABASE Tunix;
USE Tunix;


-- ==============
-- ACCOUNT
-- =============

CREATE TABLE account (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ARTIST', 'ADMIN') NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =================
-- USER
-- ===========

CREATE TABLE user (
    user_id INT PRIMARY KEY,
    display_name VARCHAR(100) UNIQUE NOT NULL,
    profile_picture_url VARCHAR(255),
    premium_trial_used BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES account(account_id) ON DELETE CASCADE
);