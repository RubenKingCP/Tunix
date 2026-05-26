Use Tunix;

INSERT INTO account (account_id, username, email, password, role)
VALUES
(1, 'user1', 'user1@mail.com', 'pass123', 'USER'),
(2, 'user2', 'user2@mail.com', 'pass123', 'USER'),
(3, 'user3', 'user3@mail.com', 'pass123', 'USER'),

(4, 'artist1', 'artist1@mail.com', 'pass123', 'ARTIST'),
(5, 'artist2', 'artist2@mail.com', 'pass123', 'ARTIST'),
(6, 'artist3', 'artist3@mail.com', 'pass123', 'ARTIST'),

(7, 'admin1', 'admin1@mail.com', 'pass123', 'ADMIN'),
(8, 'admin2', 'admin2@mail.com', 'pass123', 'ADMIN'),
(9, 'admin3', 'admin3@mail.com', 'pass123', 'ADMIN');


INSERT INTO user (id, account_id, display_name, profile_picture_url, premium, premium_trial_used, downloaded_songs_count)
VALUES
(1, 1, 'User One', NULL, FALSE, FALSE, 5),
(2, 2, 'User Two', NULL, TRUE, FALSE, 12),
(3, 3, 'User Three', NULL, FALSE, TRUE, 2);

INSERT INTO artist (id, account_id, biography, followers_count, verified)
VALUES
(1, 4, 'Electronic music producer', 1200, TRUE),
(2, 5, 'Indie pop singer', 540, FALSE),
(3, 6, 'Hip-hop artist', 3000, TRUE);

INSERT INTO admin (id, account_id)
VALUES
(1, 7),
(2, 8),
(3, 9);

INSERT INTO song (id, title, artist_id, duration, file_path_url, cover_image_url)
VALUES
(1, 'Neon Dreams', 1, 210, '/music/neon_dreams.mp3', '/img/neon.jpg'),
(2, 'Ocean Eyes', 2, 185, '/music/ocean_eyes.mp3', '/img/ocean.jpg'),
(3, 'Street Flow', 3, 240, '/music/street_flow.mp3', '/img/street.jpg');

INSERT INTO album (id, title, artist_id, release_date)
VALUES
(1, 'Electric Nights', 1, '2024-01-10'),
(2, 'Soft Echoes', 2, '2023-08-22'),
(3, 'Urban Legends', 3, '2025-03-15');

INSERT INTO album_song (id, album_id, song_id)
VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3);

INSERT INTO playlist (id, title, creator_id, is_public, created_at, updated_at)
VALUES
(1, 'My Favorites', 1, TRUE, NOW(), NOW()),
(2, 'Chill Vibes', 2, FALSE, NOW(), NOW()),
(3, 'Workout Mix', 4, TRUE, NOW(), NOW());

INSERT INTO playlist_coauthor (id, playlist_id, account_id)
VALUES
(1, 1, 4),
(2, 2, 5),
(3, 3, 1);

INSERT INTO playlist_item (id, playlist_id, song_id, position)
VALUES
(1, 1, 1, 0),
(2, 1, 2, 1),
(3, 2, 3, 0);