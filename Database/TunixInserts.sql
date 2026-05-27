-- =========================
-- ACCOUNTS (10 USERS + 10 ARTISTS + 4 ADMINS)
-- =========================
use tunix;

INSERT INTO account (account_id, username, email, password, role) VALUES
(1,  'user1',   'user1@mail.com',   'pass123', 'USER'),
(2,  'user2',   'user2@mail.com',   'pass123', 'USER'),
(3,  'user3',   'user3@mail.com',   'pass123', 'USER'),
(4,  'user4',   'user4@mail.com',   'pass123', 'USER'),
(5,  'user5',   'user5@mail.com',   'pass123', 'USER'),
(6,  'user6',   'user6@mail.com',   'pass123', 'USER'),
(7,  'user7',   'user7@mail.com',   'pass123', 'USER'),
(8,  'user8',   'user8@mail.com',   'pass123', 'USER'),
(9,  'user9',   'user9@mail.com',   'pass123', 'USER'),
(10, 'user10',  'user10@mail.com',  'pass123', 'USER'),

(11, 'artist1',  'artist1@mail.com',  'pass123', 'ARTIST'),
(12, 'artist2',  'artist2@mail.com',  'pass123', 'ARTIST'),
(13, 'artist3',  'artist3@mail.com',  'pass123', 'ARTIST'),
(14, 'artist4',  'artist4@mail.com',  'pass123', 'ARTIST'),
(15, 'artist5',  'artist5@mail.com',  'pass123', 'ARTIST'),
(16, 'artist6',  'artist6@mail.com',  'pass123', 'ARTIST'),
(17, 'artist7',  'artist7@mail.com',  'pass123', 'ARTIST'),
(18, 'artist8',  'artist8@mail.com',  'pass123', 'ARTIST'),
(19, 'artist9',  'artist9@mail.com',  'pass123', 'ARTIST'),
(20, 'artist10', 'artist10@mail.com', 'pass123', 'ARTIST'),

(21, 'admin1', 'admin1@mail.com', 'admin123', 'ADMIN'),
(22, 'admin2', 'admin2@mail.com', 'admin123', 'ADMIN'),
(23, 'admin3', 'admin3@mail.com', 'admin123', 'ADMIN'),
(24, 'admin4', 'admin4@mail.com', 'admin123', 'ADMIN');

-- =========================
-- USERS
-- =========================

INSERT INTO user (id, account_id, display_name, profile_picture_url, premium) VALUES
(1, 1,  'John Doe',       '/img/user1.jpg',  TRUE),
(2, 2,  'Jane Smith',     '/img/user2.jpg',  FALSE),
(3, 3,  'Mike Johnson',   '/img/user3.jpg',  TRUE),
(4, 4,  'Emily Brown',    '/img/user4.jpg',  FALSE),
(5, 5,  'Chris Wilson',   '/img/user5.jpg',  TRUE),
(6, 6,  'Sarah Taylor',   '/img/user6.jpg',  FALSE),
(7, 7,  'Daniel White',   '/img/user7.jpg',  TRUE),
(8, 8,  'Olivia Martin',  '/img/user8.jpg',  FALSE),
(9, 9,  'James Lee',      '/img/user9.jpg',  TRUE),
(10,10, 'Sophia Walker',  '/img/user10.jpg', FALSE);

-- =========================
-- ARTISTS
-- =========================

INSERT INTO artist (id, account_id, biography, followers_count, verified) VALUES
(1, 11, 'Synthwave producer.', 5000, TRUE),
(2, 12, 'Pop vocalist.', 8200, TRUE),
(3, 13, 'Hip hop artist.', 4300, FALSE),
(4, 14, 'Indie rock musician.', 3900, FALSE),
(5, 15, 'Electronic DJ.', 12000, TRUE),
(6, 16, 'Jazz pianist.', 2800, FALSE),
(7, 17, 'Lo-fi beatmaker.', 7600, TRUE),
(8, 18, 'Alternative singer.', 3400, FALSE),
(9, 19, 'Trap producer.', 9100, TRUE),
(10,20, 'Classical composer.', 1500, FALSE);

-- =========================
-- ADMINS
-- =========================

INSERT INTO admin (id, account_id) VALUES
(1, 21),
(2, 22),
(3, 23),
(4, 24);

-- =========================
-- SONGS
-- =========================

INSERT INTO song (id, title, artist_id, duration, file_path_url, cover_image_url) VALUES
(1, 'Neon Dreams',      1, 210, '/music/neon_dreams.mp3', '/img/neon.jpg'),
(2, 'Ocean Eyes',       2, 185, '/music/ocean_eyes.mp3', '/img/ocean.jpg'),
(3, 'Street Flow',      3, 240, '/music/street_flow.mp3', '/img/street.jpg'),
(4, 'Midnight Echoes',  4, 195, '/music/midnight.mp3', '/img/midnight.jpg'),
(5, 'Electric Pulse',   5, 225, '/music/electric.mp3', '/img/electric.jpg'),
(6, 'Blue Jazz',        6, 260, '/music/blue_jazz.mp3', '/img/jazz.jpg'),
(7, 'Rainy Nights',     7, 175, '/music/rainy.mp3', '/img/rainy.jpg'),
(8, 'Broken Lights',    8, 205, '/music/broken.mp3', '/img/broken.jpg'),
(9, '808 Heat',         9, 230, '/music/808heat.mp3', '/img/808.jpg'),
(10,'Moon Sonata X',   10, 320, '/music/moon.mp3', '/img/moon.jpg');

-- =========================
-- ALBUMS
-- =========================

INSERT INTO album (id, title, artist_id, release_date) VALUES
(1, 'Future Nights', 1, '2024-01-10'),
(2, 'Golden Hour', 2, '2024-02-14'),
(3, 'Urban Tales', 3, '2024-03-01'),
(4, 'Lost Signals', 4, '2024-03-20'),
(5, 'Bassline', 5, '2024-04-11'),
(6, 'Smooth Blue', 6, '2024-04-28'),
(7, 'Dreamscape', 7, '2024-05-07'),
(8, 'Fading Echo', 8, '2024-05-21'),
(9, 'Trap Kingdom', 9, '2024-06-15'),
(10,'Symphony One',10, '2024-07-01');

-- =========================
-- ALBUM SONGS
-- =========================

INSERT INTO album_song (album_id, song_id) VALUES
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(9,9),
(10,10);

-- =========================
-- PLAYLISTS
-- =========================

INSERT INTO playlist (id, title, creator_id, is_public) VALUES
(1, 'Workout Hits', 1, TRUE),
(2, 'Late Night', 2, TRUE),
(3, 'Chill Mix', 3, FALSE),
(4, 'Coding Playlist', 4, TRUE),
(5, 'Party Time', 5, TRUE),
(6, 'Sad Songs', 6, FALSE),
(7, 'Roadtrip', 7, TRUE),
(8, 'Focus Mode', 8, TRUE),
(9, 'Gaming Music', 9, TRUE),
(10,'Sleep Sounds',10,FALSE);

-- =========================
-- PLAYLIST COAUTHORS
-- =========================

INSERT INTO playlist_coauthor (playlist_id, account_id) VALUES
(1,2),
(2,3),
(3,4),
(4,5),
(5,6),
(6,7),
(7,8),
(8,9),
(9,10),
(10,1);

-- =========================
-- PLAYLIST ITEMS
-- =========================

INSERT INTO playlist_item (playlist_id, song_id, position) VALUES
(1,1,1),
(2,2,1),
(3,3,1),
(4,4,1),
(5,5,1),
(6,6,1),
(7,7,1),
(8,8,1),
(9,9,1),
(10,10,1);

-- =========================
-- ARTIST REQUESTS
-- =========================

INSERT INTO artist_request
(request_id, user_id, stage_name, reason, profile_picture_url, status)
VALUES
(1,1,'NightPulse','I want to upload synthwave tracks.','/img/ar1.jpg','PENDING'),
(2,2,'LunaSky','Independent singer looking for exposure.','/img/ar2.jpg','APPROVED'),
(3,3,'BeatCrafter','Producer creating hip hop beats.','/img/ar3.jpg','REJECTED'),
(4,4,'EchoLine','Alternative rock vocalist.','/img/ar4.jpg','PENDING'),
(5,5,'DJ Flux','EDM producer and DJ.','/img/ar5.jpg','PENDING'),
(6,6,'BlueSoul','Jazz piano compositions.','/img/ar6.jpg','APPROVED'),
(7,7,'LoFiDream','Lo-fi chill beats creator.','/img/ar7.jpg','PENDING'),
(8,8,'NovaWave','Experimental electronic artist.','/img/ar8.jpg','REJECTED'),
(9,9,'TrapLord','Trap producer and songwriter.','/img/ar9.jpg','PENDING'),
(10,10,'ClassicMind','Modern classical composer.','/img/ar10.jpg','PENDING');

INSERT INTO library (account_id) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10),

(11),
(12),
(13),
(14),
(15),
(16),
(17),
(18),
(19),
(20);

INSERT INTO library_song (library_id, song_id) VALUES
-- User libraries (1–10)
(1,1),(1,2),
(2,2),(2,3),
(3,3),(3,4),
(4,4),(4,5),
(5,5),(5,6),
(6,6),(6,7),
(7,7),(7,8),
(8,8),(8,9),
(9,9),(9,10),
(10,10),(10,1),

-- Artist libraries (11–20)
(11,1),
(12,2),
(13,3),
(14,4),
(15,5),
(16,6),
(17,7),
(18,8),
(19,9),
(20,10);

INSERT INTO library_album (library_id, album_id) VALUES
(1,1),(2,2),(3,3),(4,4),(5,5),
(6,6),(7,7),(8,8),(9,9),(10,10),

(11,1),(12,2),(13,3),(14,4),(15,5),
(16,6),(17,7),(18,8),(19,9),(20,10);

INSERT INTO library_playlist (library_id, playlist_id) VALUES
(1,1),(2,2),(3,3),(4,4),(5,5),
(6,6),(7,7),(8,8),(9,9),(10,10),

(11,1),(12,2),(13,3),(14,4),(15,5),
(16,6),(17,7),(18,8),(19,9),(20,10);

INSERT INTO library_artist (library_id, artist_id) VALUES
-- user libraries follow all artists
(1,1),(1,2),
(2,3),(2,4),
(3,5),(3,6),
(4,7),(4,8),
(5,9),(5,10),
(6,1),(6,2),
(7,3),(7,4),
(8,5),(8,6),
(9,7),(9,8),
(10,9),(10,10),

-- artist libraries follow themselves + others
(11,1),
(12,2),
(13,3),
(14,4),
(15,5),
(16,6),
(17,7),
(18,8),
(19,9),
(20,10);