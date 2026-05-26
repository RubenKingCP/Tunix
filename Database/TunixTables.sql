DROP DATABASE IF EXISTS Tunix;
CREATE DATABASE Tunix;
USE Tunix;


-- ==============
-- ACCOUNT
-- =============

CREATE TABLE account (
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
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
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL UNIQUE,

    display_name VARCHAR(150),
    profile_picture_url TEXT,

    premium BOOLEAN DEFAULT FALSE,
    premium_trial_used BOOLEAN DEFAULT FALSE,
    downloaded_songs_count INT DEFAULT 0,

    FOREIGN KEY (account_id) REFERENCES account(account_id)
        ON DELETE CASCADE
);


CREATE TABLE artist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL UNIQUE,

    biography TEXT,
    followers_count INT DEFAULT 0,
    verified BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (account_id) REFERENCES account(account_id)
        ON DELETE CASCADE
);

CREATE TABLE admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL UNIQUE,

    FOREIGN KEY (account_id) REFERENCES account(account_id)
        ON DELETE CASCADE
);

CREATE TABLE song (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    title VARCHAR(255) NOT NULL,
    artist_id BIGINT NOT NULL,

    duration INT NOT NULL,
    file_path_url TEXT NOT NULL,
    cover_image_url TEXT,

    FOREIGN KEY (artist_id) REFERENCES artist(id)
        ON DELETE CASCADE
);

CREATE TABLE album (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    title VARCHAR(255) NOT NULL,
    artist_id BIGINT NOT NULL,
    release_date DATE,

    FOREIGN KEY (artist_id) REFERENCES artist(id)
        ON DELETE CASCADE
);

CREATE TABLE album_song (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    album_id BIGINT NOT NULL,
    song_id BIGINT NOT NULL,

    FOREIGN KEY (album_id) REFERENCES album(id)
        ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES song(id)
        ON DELETE CASCADE
);

CREATE TABLE playlist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    title VARCHAR(255) NOT NULL,
    creator_id BIGINT NOT NULL,

    is_public BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (creator_id) REFERENCES account(account_id)
        ON DELETE CASCADE
);

CREATE TABLE playlist_coauthor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    playlist_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,

    FOREIGN KEY (playlist_id) REFERENCES playlist(id)
        ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
        ON DELETE CASCADE
);

CREATE TABLE playlist_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    playlist_id BIGINT NOT NULL,
    song_id BIGINT NOT NULL,
    position INT NOT NULL,

    FOREIGN KEY (playlist_id) REFERENCES playlist(id)
        ON DELETE CASCADE,
    FOREIGN KEY (song_id) REFERENCES song(id)
        ON DELETE CASCADE,

    UNIQUE (playlist_id, position)
);