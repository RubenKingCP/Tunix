USE Tunix;

-- =============
-- ACCOUNT (20 total: 10 users, 8 artists, 2 admins)
-- =============
INSERT INTO account (account_id, username, email, password, role) VALUES
(1,  'user1',       'john@example.com',    'pass123',  'USER'),
(2,  'jane_smith',     'jane@example.com',    'pass123',  'USER'),
(3,  'mike_jones',     'mike@example.com',    'pass123',  'USER'),
(4,  'sara_lee',       'sara@example.com',    'pass123',  'USER'),
(5,  'tom_brown',      'tom@example.com',     'pass123',  'USER'),
(6,  'lucy_white',     'lucy@example.com',    'pass123',  'USER'),
(7,  'kevin_black',    'kevin@example.com',   'pass123',  'USER'),
(8,  'nina_hall',      'nina@example.com',    'pass123',  'USER'),
(9,  'omar_king',      'omar@example.com',    'pass123',  'USER'),
(10, 'petra_wood',     'petra@example.com',   'pass123', 'USER'),
(11, 'artist_drake',   'drake@music.com',     'pass123', 'ARTIST'),
(12, 'artist_adele',   'adele@music.com',     'pass123', 'ARTIST'),
(13, 'artist_kendrick','kendrick@music.com',  'pass123', 'ARTIST'),
(14, 'artist_taylor',  'taylor@music.com',    'pass123', 'ARTIST'),
(15, 'artist_weeknd',  'weeknd@music.com',    'pass123', 'ARTIST'),
(16, 'artist_billie',  'billie@music.com',    'pass123', 'ARTIST'),
(17, 'artist_post',    'post@music.com',      'pass123', 'ARTIST'),
(18, 'artist_doja',    'doja@music.com',      'pass123', 'ARTIST'),
(19, 'artist_sza',     'sza@music.com',       'pass123', 'ARTIST'),
(20, 'artist_future',  'future@music.com',    'pass123', 'ARTIST'),
(21, 'admin_alice',    'alice@admin.com',     'pass123', 'ADMIN'),
(22, 'admin_bob',      'bob@admin.com',       'pass123', 'ADMIN');

-- =============
-- USER
-- =============
INSERT INTO user (id, account_id, display_name, profile_picture_url, premium, premium_trial_used, downloaded_songs_count) VALUES
(1,  1,  'John Doe',    'https://cdn.tunix.com/pfp/1.jpg',  TRUE,  TRUE,  12),
(2,  2,  'Jane Smith',  'https://cdn.tunix.com/pfp/2.jpg',  FALSE, FALSE, 0),
(3,  3,  'Mike Jones',  'https://cdn.tunix.com/pfp/3.jpg',  TRUE,  TRUE,  5),
(4,  4,  'Sara Lee',    'https://cdn.tunix.com/pfp/4.jpg',  FALSE, TRUE,  0),
(5,  5,  'Tom Brown',   'https://cdn.tunix.com/pfp/5.jpg',  TRUE,  TRUE,  20),
(6,  6,  'Lucy White',  'https://cdn.tunix.com/pfp/6.jpg',  FALSE, FALSE, 0),
(7,  7,  'Kevin Black', 'https://cdn.tunix.com/pfp/7.jpg',  TRUE,  TRUE,  8),
(8,  8,  'Nina Hall',   'https://cdn.tunix.com/pfp/8.jpg',  FALSE, FALSE, 0),
(9,  9,  'Omar King',   'https://cdn.tunix.com/pfp/9.jpg',  TRUE,  FALSE, 3),
(10, 10, 'Petra Wood',  'https://cdn.tunix.com/pfp/10.jpg', FALSE, FALSE, 0);

-- =============
-- ARTIST
-- =============
INSERT INTO artist (id, account_id, display_name, biography, followers_count, verified) VALUES
(1,  11, 'Drake',    'Rapper and singer from Toronto.',           54000000, TRUE),
(2,  12, 'Adele',    'Grammy-winning British singer-songwriter.', 48000000, TRUE),
(3,  13, 'Kendrick', 'Pulitzer Prize-winning rapper from Compton.',38000000, TRUE),
(4,  14, 'Taylor S', 'Pop icon and record-breaking songwriter.',   72000000, TRUE),
(5,  15, 'The Weeknd','R&B and pop artist from Toronto.',          61000000, TRUE),
(6,  16, 'Billie E', 'Alternative pop artist from Los Angeles.',   44000000, TRUE),
(7,  17, 'Post Malone','Hip-hop and pop artist from Texas.',       39000000, TRUE),
(8,  18, 'Doja Cat', 'Rapper and singer from Los Angeles.',        35000000, TRUE),
(9,  19, 'SZA',      'R&B singer-songwriter from New Jersey.',     28000000, TRUE),
(10, 20, 'Future',   'Trap pioneer from Atlanta.',                 31000000, TRUE);

-- =============
-- ADMIN
-- =============
INSERT INTO admin (id, account_id) VALUES
(1, 21),
(2, 22);

-- =============
-- SONG (10 per artist = 100 total, we do 10 spread across artists)
-- =============
INSERT INTO song (id, title, artist_id, duration, file_path_url, cover_image_url) VALUES
(1,  'God\'s Plan',        1, 198, 'https://cdn.tunix.com/songs/1.mp3',  'https://cdn.tunix.com/covers/1.jpg'),
(2,  'Hotline Bling',      1, 267, 'https://cdn.tunix.com/songs/2.mp3',  'https://cdn.tunix.com/covers/2.jpg'),
(3,  'Hello',              2, 295, 'https://cdn.tunix.com/songs/3.mp3',  'https://cdn.tunix.com/covers/3.jpg'),
(4,  'Rolling in the Deep',2, 228, 'https://cdn.tunix.com/songs/4.mp3',  'https://cdn.tunix.com/covers/4.jpg'),
(5,  'HUMBLE.',            3, 177, 'https://cdn.tunix.com/songs/5.mp3',  'https://cdn.tunix.com/covers/5.jpg'),
(6,  'DNA.',               3, 185, 'https://cdn.tunix.com/songs/6.mp3',  'https://cdn.tunix.com/covers/6.jpg'),
(7,  'Shake It Off',       4, 219, 'https://cdn.tunix.com/songs/7.mp3',  'https://cdn.tunix.com/covers/7.jpg'),
(8,  'Blank Space',        4, 231, 'https://cdn.tunix.com/songs/8.mp3',  'https://cdn.tunix.com/covers/8.jpg'),
(9,  'Blinding Lights',    5, 200, 'https://cdn.tunix.com/songs/9.mp3',  'https://cdn.tunix.com/covers/9.jpg'),
(10, 'Starboy',            5, 230, 'https://cdn.tunix.com/songs/10.mp3', 'https://cdn.tunix.com/covers/10.jpg'),
(11, 'Bad Guy',            6, 194, 'https://cdn.tunix.com/songs/11.mp3', 'https://cdn.tunix.com/covers/11.jpg'),
(12, 'Happier Than Ever',  6, 298, 'https://cdn.tunix.com/songs/12.mp3', 'https://cdn.tunix.com/covers/12.jpg'),
(13, 'Circles',            7, 215, 'https://cdn.tunix.com/songs/13.mp3', 'https://cdn.tunix.com/covers/13.jpg'),
(14, 'Rockstar',           7, 218, 'https://cdn.tunix.com/songs/14.mp3', 'https://cdn.tunix.com/covers/14.jpg'),
(15, 'Say So',             8, 237, 'https://cdn.tunix.com/songs/15.mp3', 'https://cdn.tunix.com/covers/15.jpg'),
(16, 'Kiss Me More',       8, 208, 'https://cdn.tunix.com/songs/16.mp3', 'https://cdn.tunix.com/covers/16.jpg'),
(17, 'Good Days',          9, 272, 'https://cdn.tunix.com/songs/17.mp3', 'https://cdn.tunix.com/covers/17.jpg'),
(18, 'Kill Bill',          9, 153, 'https://cdn.tunix.com/songs/18.mp3', 'https://cdn.tunix.com/covers/18.jpg'),
(19, 'Mask Off',           10, 220,'https://cdn.tunix.com/songs/19.mp3', 'https://cdn.tunix.com/covers/19.jpg'),
(20, 'Life Is Good',       10, 240,'https://cdn.tunix.com/songs/20.mp3', 'https://cdn.tunix.com/covers/20.jpg');

-- =============
-- ALBUM
-- =============
INSERT INTO album (id, title, artist_id, release_date) VALUES
(1,  'Scorpion',        1,  '2018-06-29'),
(2,  '21',              2,  '2011-01-24'),
(3,  'DAMN.',           3,  '2017-04-14'),
(4,  '1989',            4,  '2014-10-27'),
(5,  'After Hours',     5,  '2020-03-20'),
(6,  'Happier Than Ever',6, '2021-07-30'),
(7,  'Hollywood\'s Bleeding',7,'2019-09-06'),
(8,  'Planet Her',      8,  '2021-06-25'),
(9,  'SOS',             9,  '2022-12-09'),
(10, 'HNDRXX',          10, '2017-02-24');

-- =============
-- ALBUM_SONG (10 join rows + 10 extra = 20)
-- =============
INSERT INTO album_song (album_id, song_id) VALUES
-- Base 10
(1,  1),
(1,  2),
(2,  3),
(2,  4),
(3,  5),
(3,  6),
(4,  7),
(4,  8),
(5,  9),
(5,  10),
-- Extra 10
(6,  11),
(6,  12),
(7,  13),
(7,  14),
(8,  15),
(8,  16),
(9,  17),
(9,  18),
(10, 19),
(10, 20);

-- =============
-- PLAYLIST
-- =============
INSERT INTO playlist (id, title, creator_id, is_public) VALUES
(1,  'Morning Vibes',     1,  TRUE),
(2,  'Late Night Feels',  2,  FALSE),
(3,  'Workout Bangers',   3,  TRUE),
(4,  'Chill Sunday',      4,  TRUE),
(5,  'Road Trip Mix',     5,  FALSE),
(6,  'Study Session',     6,  TRUE),
(7,  'Party Hits',        7,  TRUE),
(8,  'Throwback Jams',    8,  FALSE),
(9,  'RnB Lounge',        9,  TRUE),
(10, 'Top Charts',        10, TRUE);

-- =============
-- PLAYLIST_COAUTHOR (10 rows)
-- =============
INSERT INTO playlist_coauthor (playlist_id, account_id) VALUES
(1,  2),
(2,  3),
(3,  4),
(4,  5),
(5,  6),
(6,  7),
(7,  8),
(8,  9),
(9,  10),
(10, 1);

-- =============
-- PLAYLIST_ITEM (10 base + 10 extra = 20, unique playlist_id+position combos)
-- =============
INSERT INTO playlist_item (playlist_id, song_id, position) VALUES
-- Base 10
(1,  1,  1),
(1,  3,  2),
(2,  5,  1),
(2,  9,  2),
(3,  2,  1),
(3,  6,  2),
(4,  11, 1),
(4,  15, 2),
(5,  7,  1),
(5,  13, 2),
-- Extra 10
(6,  17, 1),
(6,  19, 2),
(7,  4,  1),
(7,  8,  2),
(8,  10, 1),
(8,  12, 2),
(9,  14, 1),
(9,  16, 2),
(10, 18, 1),
(10, 20, 2);

-- =============
-- ARTIST_REQUEST (10 rows from users)
-- =============
INSERT INTO artist_request (user_id, stage_name, reason, profile_picture_url, status, reviewed_at) VALUES
(1,  'DJ Johnny',    'I produce electronic music professionally.',     'https://cdn.tunix.com/req/1.jpg', 'APPROVED', '2024-02-10 10:00:00'),
(2,  'Jane Vibes',   'Singer-songwriter with 5 years experience.',     'https://cdn.tunix.com/req/2.jpg', 'PENDING',  NULL),
(3,  'MC Mikey',     'Rapper with 3 released mixtapes.',               'https://cdn.tunix.com/req/3.jpg', 'REJECTED', '2024-01-15 14:00:00'),
(4,  'Sara Melodies','Classically trained pianist.',                   'https://cdn.tunix.com/req/4.jpg', 'PENDING',  NULL),
(5,  'Tom Beats',    'Beat producer with YouTube channel.',            'https://cdn.tunix.com/req/5.jpg', 'APPROVED', '2024-03-05 09:00:00'),
(6,  'Lucy Luna',    'Jazz vocalist performing live for 3 years.',     'https://cdn.tunix.com/req/6.jpg', 'PENDING',  NULL),
(7,  'Kev Rhymes',   'Hip-hop artist with 10k SoundCloud followers.',  'https://cdn.tunix.com/req/7.jpg', 'APPROVED', '2024-03-20 11:00:00'),
(8,  'Nina Notes',   'Indie pop artist with self-released EP.',        'https://cdn.tunix.com/req/8.jpg', 'REJECTED', '2024-02-28 16:00:00'),
(9,  'Omar Waves',   'R&B singer from New York.',                      'https://cdn.tunix.com/req/9.jpg', 'PENDING',  NULL),
(10, 'Petra Pop',    'Pop artist with viral TikTok covers.',           'https://cdn.tunix.com/req/10.jpg','APPROVED', '2024-04-01 08:00:00');

-- =============
-- LIBRARY (one per account, 10 users + 10 artists = 20)
-- =============
INSERT INTO library (id, account_id) VALUES
(1,  1),  (2,  2),  (3,  3),  (4,  4),  (5,  5),
(6,  6),  (7,  7),  (8,  8),  (9,  9),  (10, 10),
(11, 11), (12, 12), (13, 13), (14, 14), (15, 15),
(16, 16), (17, 17), (18, 18), (19, 19), (20, 20);

-- =============
-- LIBRARY_SONG (10 base + 10 extra = 20)
-- =============
INSERT INTO library_song (library_id, song_id) VALUES
(1, 1),  (2, 3),  (3, 5),  (4, 7),  (5, 9),
(6, 11), (7, 13), (8, 15), (9, 17), (10, 19),
-- Extra 10
(1, 2),  (2, 4),  (3, 6),  (4, 8),  (5, 10),
(6, 12), (7, 14), (8, 16), (9, 18), (10, 20);

-- =============
-- LIBRARY_ALBUM (10 base + 10 extra = 20)
-- =============
INSERT INTO library_album (library_id, album_id) VALUES
(1, 1),  (2, 2),  (3, 3),  (4, 4),  (5, 5),
(6, 6),  (7, 7),  (8, 8),  (9, 9),  (10, 10),
-- Extra 10
(11, 1), (12, 2), (13, 3), (14, 4), (15, 5),
(16, 6), (17, 7), (18, 8), (19, 9), (20, 10);

-- =============
-- LIBRARY_PLAYLIST (10 base + 10 extra = 20)
-- =============
INSERT INTO library_playlist (library_id, playlist_id) VALUES
(1, 1),  (2, 2),  (3, 3),  (4, 4),  (5, 5),
(6, 6),  (7, 7),  (8, 8),  (9, 9),  (10, 10),
-- Extra 10
(11, 1), (12, 2), (13, 3), (14, 4), (15, 5),
(16, 6), (17, 7), (18, 8), (19, 9), (20, 10);

-- =============
-- LIBRARY_ARTIST (10 base + 10 extra = 20)
-- =============
INSERT INTO library_artist (library_id, artist_id) VALUES
(1, 2),  (2, 3),  (3, 4),  (4, 5),  (5, 6),
(6, 7),  (7, 8),  (8, 9),  (9, 10), (10, 1),
-- Extra 10
(11, 1), (12, 2), (13, 3), (14, 4), (15, 5),
(16, 6), (17, 7), (18, 8), (19, 9), (20, 10);