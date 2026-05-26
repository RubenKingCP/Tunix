Use Tunix;

USE Tunix;

INSERT INTO account (username, email, password, role)
VALUES
('user1', 'user1@mail.com', 'pass123', 'USER'),
('user2', 'user2@mail.com', 'pass123', 'USER'),
('user3', 'user3@mail.com', 'pass123', 'USER'),
('user4', 'user4@mail.com', 'pass123', 'USER'),
('user5', 'user5@mail.com', 'pass123', 'USER'),
('user6', 'user6@mail.com', 'pass123', 'USER'),
('user7', 'user7@mail.com', 'pass123', 'USER'),
('user8', 'user8@mail.com', 'pass123', 'USER'),
('user9', 'user9@mail.com', 'pass123', 'USER'),
('user10', 'user10@mail.com', 'pass123', 'USER'),

('artist1', 'artist1@mail.com', 'pass123', 'ARTIST'),
('artist2', 'artist2@mail.com', 'pass123', 'ARTIST'),
('artist3', 'artist3@mail.com', 'pass123', 'ARTIST'),

('admin1', 'admin1@mail.com', 'pass123', 'ADMIN');

INSERT INTO user (account_id, display_name, profile_picture_url, premium, premium_trial_used, downloaded_songs_count)
VALUES
(1, 'User One', NULL, FALSE, FALSE, 5),
(2, 'User Two', NULL, TRUE, FALSE, 12),
(3, 'User Three', NULL, FALSE, TRUE, 2),
(4, 'User Four', NULL, FALSE, FALSE, 0),
(5, 'User Five', NULL, FALSE, FALSE, 0),
(6, 'User Six', NULL, FALSE, FALSE, 0),
(7, 'User Seven', NULL, FALSE, FALSE, 0),
(8, 'User Eight', NULL, FALSE, FALSE, 0),
(9, 'User Nine', NULL, FALSE, FALSE, 0),
(10, 'User Ten', NULL, FALSE, FALSE, 0);

INSERT INTO artist (account_id, biography, followers_count, verified)
VALUES
(11, 'Electronic music producer', 1200, TRUE),
(12, 'Indie pop singer', 540, FALSE),
(13, 'Hip-hop artist', 3000, TRUE);


INSERT INTO admin (account_id)
VALUES
(14);

INSERT INTO song (title, artist_id, duration, file_path_url, cover_image_url)
VALUES
('Neon Dreams', 1, 210, '/music/neon_dreams.mp3', '/img/neon.jpg'),
('Ocean Eyes', 2, 185, '/music/ocean_eyes.mp3', '/img/ocean.jpg'),
('Street Flow', 3, 240, '/music/street_flow.mp3', '/img/street.jpg');

INSERT INTO album (title, artist_id, release_date)
VALUES
('Electric Nights', 1, '2024-01-10'),
('Soft Echoes', 2, '2023-08-22'),
('Urban Legends', 3, '2025-03-15');

INSERT INTO album_song (id, album_id, song_id)
VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3);

INSERT INTO playlist (title, creator_id, is_public, created_at, updated_at)
VALUES
('My Favorites', 1, TRUE, NOW(), NOW()),
('Chill Vibes', 2, FALSE, NOW(), NOW()),
('Workout Mix', 4, TRUE, NOW(), NOW());

INSERT INTO playlist_coauthor (id, playlist_id, account_id)
VALUES
(1, 1, 4),
(2, 2, 5),
(3, 3, 1);

INSERT INTO playlist_item (playlist_id, song_id, position)
VALUES
(1, 1, 0),
(1, 2, 1),
(2, 3, 0);

INSERT INTO artist_request (user_id, stage_name, reason, profile_picture_url, status, requested_at, reviewed_at)
VALUES
(1, 'AstraVox', 'Electronic artist blending ambient and synthwave.', 'https://img.com/astravox.jpg', 'PENDING', NOW(), NULL),
(2, 'LunaBeat', 'Lo-fi producer creating chill beats.', 'https://img.com/lunabeat.jpg', 'PENDING', NOW(), NULL),
(3, 'EchoNova', 'Experimental pop artist.', 'https://img.com/echonova.jpg', 'PENDING', NOW(), NULL),
(4, 'DJ Kairo', 'Club DJ techno & house.', 'https://img.com/djkairo.jpg', 'APPROVED', NOW(), NOW()),
(5, 'NeonDrift', 'Cyberpunk electronic producer.', 'https://img.com/neondrift.jpg', 'PENDING', NOW(), NULL),
(6, 'Skyline R', 'Hip-hop storyteller.', 'https://img.com/skylineR.jpg', 'REJECTED', NOW(), NOW()),
(7, 'VelvetTone', 'R&B soulful singer.', 'https://img.com/velvettone.jpg', 'PENDING', NOW(), NULL),
(8, 'BassPhantom', 'Hard EDM producer.', 'https://img.com/bassphantom.jpg', 'PENDING', NOW(), NULL),
(9, 'AriaWave', 'Indie pop songwriter.', 'https://img.com/ariawave.jpg', 'APPROVED', NOW(), NOW()),
(10, 'ZeroPulse', 'Dark industrial artist.', 'https://img.com/zeropulse.jpg', 'PENDING', NOW(), NULL);