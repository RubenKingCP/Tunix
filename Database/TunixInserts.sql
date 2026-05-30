-- =============================================
-- TUNIX DATABASE SEED DATA
-- =============================================

USE Tunix;

-- =============================================
-- ACCOUNTS
-- =============================================
-- Active users (1-5)
INSERT INTO account (account_id, username, email, password, role, is_banned, ban_reason, warning_count) VALUES
(1,  'user1',        'jdoe@email.com',        'pass123',  'USER',   FALSE, NULL, 0),
(2,  'sarahm',      'sarah.m@email.com',     'pass123',  'USER',   FALSE, NULL, 1),
(3,  'mike_b',      'mike.b@email.com',      'pass123',  'USER',   FALSE, NULL, 0),
(4,  'nia_w',       'nia.w@email.com',       '$2b$12$hashedpw4',  'USER',   FALSE, NULL, 2),
(5,  'carlos_r',    'carlos.r@email.com',    '$2b$12$hashedpw5',  'USER',   FALSE, NULL, 0),

-- Active artists (6-9)
(6,  'neon_pulse',  'neon.pulse@music.com',  'pass123',  'ARTIST', FALSE, NULL, 1),
(7,  'velvet_echo', 'velvet.echo@music.com', 'pass123',  'ARTIST', FALSE, NULL, 2),
(8,  'drift_wave',  'drift.wave@music.com',  '$2b$12$hashedpw8',  'ARTIST', FALSE, NULL, 0),
(9,  'luna_skye',   'luna.skye@music.com',   '$2b$12$hashedpw9',  'ARTIST', FALSE, NULL, 0),

-- Banned artists (10-12)
(10, 'rage_mxr',    'rage.mxr@music.com',    'pass123', 'ARTIST', TRUE,  'Repeated copyright violations', 3),
(11, 'dropkingz',   'dropkingz@music.com',   '$2b$12$hashedpwB', 'ARTIST', TRUE,  'Harassment of other artists',   2),
(12, 'ghostnote',   'ghostnote@music.com',   '$2b$12$hashedpwC', 'ARTIST', TRUE,  'Fraudulent streaming activity', 4),

-- Admins (13-14)
(13, 'admin_alex',  'admin.alex@tunix.com',  'pass123', 'ADMIN',  FALSE, NULL, 0),
(14, 'admin_maya',  'admin.maya@tunix.com',  '$2b$12$hashedpwE', 'ADMIN',  FALSE, NULL, 0);


-- =============================================
-- WARNINGS
-- =============================================
INSERT INTO account_warning (account_id, reason, warned_by) VALUES
-- sarahm (account 2) - 1 warning
(2, 'Sharing unauthorized download links in community posts',          13),

-- nia_w (account 4) - 2 warnings
(4, 'Inappropriate playlist titles violating community guidelines',    13),
(4, 'Spamming artist comment sections with promotional content',       14),

-- neon_pulse (account 6) - 1 warning
(6, 'Uploaded song with uncleared sample without proper attribution',  13),

-- velvet_echo (account 7) - 2 warnings
(7, 'Misleading track titles to game discovery algorithm',             14),
(7, 'Submitted duplicate songs under different names',                 13),

-- rage_mxr (account 10) - 3 warnings (now banned)
(10, 'First copyright strike — unlicensed use of sampled material',   13),
(10, 'Second copyright strike — continued violation after warning',    13),
(10, 'Third copyright strike — uploaded entire copyrighted album',     14),

-- dropkingz (account 11) - 2 warnings (now banned)
(11, 'Sent threatening messages to competing artists',                 14),
(11, 'Posted defamatory content targeting another artist profile',     13),

-- ghostnote (account 12) - 4 warnings (now banned)
(12, 'Coordinated fake stream inflation detected',                     13),
(12, 'Second instance of bot-driven stream manipulation',              14),
(12, 'Sold fake streams to third-party artists',                       13),
(12, 'Created fake accounts to inflate follower count',                14);


-- =============================================
-- USERS
-- =============================================
INSERT INTO user (id, account_id, display_name, profile_picture_url, premium, premium_trial_used, downloaded_songs_count) VALUES
(1, 1,  'John Doe',    'https://cdn.tunix.io/avatars/jdoe.jpg',    TRUE,  TRUE,  12),
(2, 2,  'Sarah M',     'https://cdn.tunix.io/avatars/sarah.jpg',   FALSE, FALSE, 0),
(3, 3,  'Mike B',      'https://cdn.tunix.io/avatars/mikeb.jpg',   TRUE,  TRUE,  7),
(4, 4,  'Nia W',       'https://cdn.tunix.io/avatars/niaw.jpg',    FALSE, TRUE,  0),
(5, 5,  'Carlos R',    'https://cdn.tunix.io/avatars/carlos.jpg',  TRUE,  TRUE,  20);


-- =============================================
-- ARTISTS
-- =============================================
INSERT INTO artist (id, account_id, biography, followers_count, verified, display_name) VALUES
(1, 6,  'Neon Pulse blends synthwave and dark electro. Based in Berlin.',                    84200, TRUE,  'Neon Pulse'),
(2, 7,  'Velvet Echo crafts atmospheric indie folk from the Pacific Northwest.',             51000, TRUE,  'Velvet Echo'),
(3, 8,  'Drift Wave is a lo-fi chill hop producer active since 2018.',                      39000, FALSE, 'Drift Wave'),
(4, 9,  'Luna Skye mixes dream pop with ambient soundscapes.',                               62500, TRUE,  'Luna Skye'),
(5, 10, 'Rage Mxr — account terminated due to repeated policy violations.',                     0, FALSE, 'Rage Mxr'),
(6, 11, 'Dropkingz — account suspended for harassment of platform members.',                    0, FALSE, 'Dropkingz'),
(7, 12, 'Ghostnote — account banned for fraudulent streaming activity.',                        0, FALSE, 'Ghostnote');


-- =============================================
-- ADMINS
-- =============================================
INSERT INTO admin (id, account_id) VALUES
(1, 13),
(2, 14);


-- =============================================
-- SONGS — 20 per active artist (IDs 1–80)
-- =============================================

-- Neon Pulse (artist_id = 1) — songs 1-20
INSERT INTO song (id, title, artist_id, duration, file_path_url, cover_image_url) VALUES
(1,  'Midnight Grid',           1, 214, 'https://cdn.tunix.io/songs/np_001.mp3', 'https://cdn.tunix.io/covers/np_001.jpg'),
(2,  'Neon Veins',              1, 198, 'https://cdn.tunix.io/songs/np_002.mp3', 'https://cdn.tunix.io/covers/np_002.jpg'),
(3,  'Static Dreams',           1, 231, 'https://cdn.tunix.io/songs/np_003.mp3', 'https://cdn.tunix.io/covers/np_003.jpg'),
(4,  'Pulse City',              1, 245, 'https://cdn.tunix.io/songs/np_004.mp3', 'https://cdn.tunix.io/covers/np_004.jpg'),
(5,  'Chrome Heart',            1, 207, 'https://cdn.tunix.io/songs/np_005.mp3', 'https://cdn.tunix.io/covers/np_005.jpg'),
(6,  'Ultraviolet Rift',        1, 263, 'https://cdn.tunix.io/songs/np_006.mp3', 'https://cdn.tunix.io/covers/np_006.jpg'),
(7,  'Dark Prism',              1, 189, 'https://cdn.tunix.io/songs/np_007.mp3', 'https://cdn.tunix.io/covers/np_007.jpg'),
(8,  'Signal Overload',         1, 218, 'https://cdn.tunix.io/songs/np_008.mp3', 'https://cdn.tunix.io/covers/np_008.jpg'),
(9,  'Binary Sunset',           1, 252, 'https://cdn.tunix.io/songs/np_009.mp3', 'https://cdn.tunix.io/covers/np_009.jpg'),
(10, 'Voltage',                 1, 174, 'https://cdn.tunix.io/songs/np_010.mp3', 'https://cdn.tunix.io/covers/np_010.jpg'),
(11, 'Phantom Circuit',         1, 236, 'https://cdn.tunix.io/songs/np_011.mp3', 'https://cdn.tunix.io/covers/np_011.jpg'),
(12, 'Glass Horizon',           1, 201, 'https://cdn.tunix.io/songs/np_012.mp3', 'https://cdn.tunix.io/covers/np_012.jpg'),
(13, 'The Last Frequency',      1, 289, 'https://cdn.tunix.io/songs/np_013.mp3', 'https://cdn.tunix.io/covers/np_013.jpg'),
(14, 'Neural Rush',             1, 193, 'https://cdn.tunix.io/songs/np_014.mp3', 'https://cdn.tunix.io/covers/np_014.jpg'),
(15, 'Infrared',                1, 222, 'https://cdn.tunix.io/songs/np_015.mp3', 'https://cdn.tunix.io/covers/np_015.jpg'),
(16, 'Synthetic Dawn',          1, 244, 'https://cdn.tunix.io/songs/np_016.mp3', 'https://cdn.tunix.io/covers/np_016.jpg'),
(17, 'Laser Maze',              1, 209, 'https://cdn.tunix.io/songs/np_017.mp3', 'https://cdn.tunix.io/covers/np_017.jpg'),
(18, 'Electric Ghost',          1, 230, 'https://cdn.tunix.io/songs/np_018.mp3', 'https://cdn.tunix.io/covers/np_018.jpg'),
(19, 'Resonance Chamber',       1, 256, 'https://cdn.tunix.io/songs/np_019.mp3', 'https://cdn.tunix.io/covers/np_019.jpg'),
(20, 'Neon Requiem',            1, 278, 'https://cdn.tunix.io/songs/np_020.mp3', 'https://cdn.tunix.io/covers/np_020.jpg'),

-- Velvet Echo (artist_id = 2) — songs 21-40
(21, 'Porch Light',             2, 203, 'https://cdn.tunix.io/songs/ve_001.mp3', 'https://cdn.tunix.io/covers/ve_001.jpg'),
(22, 'Cedar Creek',             2, 247, 'https://cdn.tunix.io/songs/ve_002.mp3', 'https://cdn.tunix.io/covers/ve_002.jpg'),
(23, 'Salt & Smoke',            2, 218, 'https://cdn.tunix.io/songs/ve_003.mp3', 'https://cdn.tunix.io/covers/ve_003.jpg'),
(24, 'Overgrown',               2, 265, 'https://cdn.tunix.io/songs/ve_004.mp3', 'https://cdn.tunix.io/covers/ve_004.jpg'),
(25, 'Hollow Hills',            2, 231, 'https://cdn.tunix.io/songs/ve_005.mp3', 'https://cdn.tunix.io/covers/ve_005.jpg'),
(26, 'Morning Rust',            2, 194, 'https://cdn.tunix.io/songs/ve_006.mp3', 'https://cdn.tunix.io/covers/ve_006.jpg'),
(27, 'Ember Road',              2, 259, 'https://cdn.tunix.io/songs/ve_007.mp3', 'https://cdn.tunix.io/covers/ve_007.jpg'),
(28, 'Barefoot on Glass',       2, 214, 'https://cdn.tunix.io/songs/ve_008.mp3', 'https://cdn.tunix.io/covers/ve_008.jpg'),
(29, 'Foxglove',                2, 243, 'https://cdn.tunix.io/songs/ve_009.mp3', 'https://cdn.tunix.io/covers/ve_009.jpg'),
(30, 'Winter Thread',           2, 272, 'https://cdn.tunix.io/songs/ve_010.mp3', 'https://cdn.tunix.io/covers/ve_010.jpg'),
(31, 'Silverline',              2, 199, 'https://cdn.tunix.io/songs/ve_011.mp3', 'https://cdn.tunix.io/covers/ve_011.jpg'),
(32, 'The Orchard',             2, 236, 'https://cdn.tunix.io/songs/ve_012.mp3', 'https://cdn.tunix.io/covers/ve_012.jpg'),
(33, 'Tangled Roots',           2, 221, 'https://cdn.tunix.io/songs/ve_013.mp3', 'https://cdn.tunix.io/covers/ve_013.jpg'),
(34, 'June Haze',               2, 248, 'https://cdn.tunix.io/songs/ve_014.mp3', 'https://cdn.tunix.io/covers/ve_014.jpg'),
(35, 'Quiet Cartography',       2, 287, 'https://cdn.tunix.io/songs/ve_015.mp3', 'https://cdn.tunix.io/covers/ve_015.jpg'),
(36, 'Swallowed by the Field',  2, 304, 'https://cdn.tunix.io/songs/ve_016.mp3', 'https://cdn.tunix.io/covers/ve_016.jpg'),
(37, 'River Mouth',             2, 228, 'https://cdn.tunix.io/songs/ve_017.mp3', 'https://cdn.tunix.io/covers/ve_017.jpg'),
(38, 'Still Life',              2, 213, 'https://cdn.tunix.io/songs/ve_018.mp3', 'https://cdn.tunix.io/covers/ve_018.jpg'),
(39, 'Ghost Season',            2, 252, 'https://cdn.tunix.io/songs/ve_019.mp3', 'https://cdn.tunix.io/covers/ve_019.jpg'),
(40, 'Last Song Before Autumn', 2, 296, 'https://cdn.tunix.io/songs/ve_020.mp3', 'https://cdn.tunix.io/covers/ve_020.jpg'),

-- Drift Wave (artist_id = 3) — songs 41-60
(41, 'Afternoon Rain',          3, 175, 'https://cdn.tunix.io/songs/dw_001.mp3', 'https://cdn.tunix.io/covers/dw_001.jpg'),
(42, 'Slow Ride Home',          3, 192, 'https://cdn.tunix.io/songs/dw_002.mp3', 'https://cdn.tunix.io/covers/dw_002.jpg'),
(43, 'Sunday Noodles',          3, 163, 'https://cdn.tunix.io/songs/dw_003.mp3', 'https://cdn.tunix.io/covers/dw_003.jpg'),
(44, 'Warm Blanket',            3, 188, 'https://cdn.tunix.io/songs/dw_004.mp3', 'https://cdn.tunix.io/covers/dw_004.jpg'),
(45, 'City Window',             3, 204, 'https://cdn.tunix.io/songs/dw_005.mp3', 'https://cdn.tunix.io/covers/dw_005.jpg'),
(46, 'Cassette Tape No.3',      3, 171, 'https://cdn.tunix.io/songs/dw_006.mp3', 'https://cdn.tunix.io/covers/dw_006.jpg'),
(47, 'Rooftop Garden',          3, 198, 'https://cdn.tunix.io/songs/dw_007.mp3', 'https://cdn.tunix.io/covers/dw_007.jpg'),
(48, 'Foggy Commute',           3, 183, 'https://cdn.tunix.io/songs/dw_008.mp3', 'https://cdn.tunix.io/covers/dw_008.jpg'),
(49, 'Late Night Ramen',        3, 207, 'https://cdn.tunix.io/songs/dw_009.mp3', 'https://cdn.tunix.io/covers/dw_009.jpg'),
(50, 'Study Session',           3, 195, 'https://cdn.tunix.io/songs/dw_010.mp3', 'https://cdn.tunix.io/covers/dw_010.jpg'),
(51, 'Paperback Sky',           3, 179, 'https://cdn.tunix.io/songs/dw_011.mp3', 'https://cdn.tunix.io/covers/dw_011.jpg'),
(52, 'Half Awake',              3, 211, 'https://cdn.tunix.io/songs/dw_012.mp3', 'https://cdn.tunix.io/covers/dw_012.jpg'),
(53, 'Gentle Loop',             3, 167, 'https://cdn.tunix.io/songs/dw_013.mp3', 'https://cdn.tunix.io/covers/dw_013.jpg'),
(54, 'Tea Time',                3, 185, 'https://cdn.tunix.io/songs/dw_014.mp3', 'https://cdn.tunix.io/covers/dw_014.jpg'),
(55, 'Bicycle Path',            3, 193, 'https://cdn.tunix.io/songs/dw_015.mp3', 'https://cdn.tunix.io/covers/dw_015.jpg'),
(56, 'Overgrown Arcade',        3, 218, 'https://cdn.tunix.io/songs/dw_016.mp3', 'https://cdn.tunix.io/covers/dw_016.jpg'),
(57, 'Amber Hour',              3, 201, 'https://cdn.tunix.io/songs/dw_017.mp3', 'https://cdn.tunix.io/covers/dw_017.jpg'),
(58, 'Drift Off',               3, 226, 'https://cdn.tunix.io/songs/dw_018.mp3', 'https://cdn.tunix.io/covers/dw_018.jpg'),
(59, 'Interlude No.7',          3, 158, 'https://cdn.tunix.io/songs/dw_019.mp3', 'https://cdn.tunix.io/covers/dw_019.jpg'),
(60, 'The Last Tape',           3, 245, 'https://cdn.tunix.io/songs/dw_020.mp3', 'https://cdn.tunix.io/covers/dw_020.jpg'),

-- Luna Skye (artist_id = 4) — songs 61-80
(61, 'Celestial Drift',         4, 238, 'https://cdn.tunix.io/songs/ls_001.mp3', 'https://cdn.tunix.io/covers/ls_001.jpg'),
(62, 'Moonpool',                4, 254, 'https://cdn.tunix.io/songs/ls_002.mp3', 'https://cdn.tunix.io/covers/ls_002.jpg'),
(63, 'Stardust Lullaby',        4, 271, 'https://cdn.tunix.io/songs/ls_003.mp3', 'https://cdn.tunix.io/covers/ls_003.jpg'),
(64, 'Aurora Fade',             4, 246, 'https://cdn.tunix.io/songs/ls_004.mp3', 'https://cdn.tunix.io/covers/ls_004.jpg'),
(65, 'Orbit',                   4, 217, 'https://cdn.tunix.io/songs/ls_005.mp3', 'https://cdn.tunix.io/covers/ls_005.jpg'),
(66, 'Halo Weather',            4, 263, 'https://cdn.tunix.io/songs/ls_006.mp3', 'https://cdn.tunix.io/covers/ls_006.jpg'),
(67, 'Solstice Dream',          4, 295, 'https://cdn.tunix.io/songs/ls_007.mp3', 'https://cdn.tunix.io/covers/ls_007.jpg'),
(68, 'Pale Blue Signal',        4, 231, 'https://cdn.tunix.io/songs/ls_008.mp3', 'https://cdn.tunix.io/covers/ls_008.jpg'),
(69, 'Cloud Cartography',       4, 258, 'https://cdn.tunix.io/songs/ls_009.mp3', 'https://cdn.tunix.io/covers/ls_009.jpg'),
(70, 'Lunar Static',            4, 242, 'https://cdn.tunix.io/songs/ls_010.mp3', 'https://cdn.tunix.io/covers/ls_010.jpg'),
(71, 'Aphelion',                4, 283, 'https://cdn.tunix.io/songs/ls_011.mp3', 'https://cdn.tunix.io/covers/ls_011.jpg'),
(72, 'Vellichor',               4, 269, 'https://cdn.tunix.io/songs/ls_012.mp3', 'https://cdn.tunix.io/covers/ls_012.jpg'),
(73, 'Tide & Echo',             4, 237, 'https://cdn.tunix.io/songs/ls_013.mp3', 'https://cdn.tunix.io/covers/ls_013.jpg'),
(74, 'Quiet Universe',          4, 312, 'https://cdn.tunix.io/songs/ls_014.mp3', 'https://cdn.tunix.io/covers/ls_014.jpg'),
(75, 'Nebula Pop',              4, 224, 'https://cdn.tunix.io/songs/ls_015.mp3', 'https://cdn.tunix.io/covers/ls_015.jpg'),
(76, 'Soft Gravity',            4, 248, 'https://cdn.tunix.io/songs/ls_016.mp3', 'https://cdn.tunix.io/covers/ls_016.jpg'),
(77, 'Tidal Reverie',           4, 265, 'https://cdn.tunix.io/songs/ls_017.mp3', 'https://cdn.tunix.io/covers/ls_017.jpg'),
(78, 'Phases',                  4, 291, 'https://cdn.tunix.io/songs/ls_018.mp3', 'https://cdn.tunix.io/covers/ls_018.jpg'),
(79, 'The Sky Remembers',       4, 307, 'https://cdn.tunix.io/songs/ls_019.mp3', 'https://cdn.tunix.io/covers/ls_019.jpg'),
(80, 'Last Light (Reprise)',     4, 334, 'https://cdn.tunix.io/songs/ls_020.mp3', 'https://cdn.tunix.io/covers/ls_020.jpg');


-- =============================================
-- ALBUMS — 2 per artist, 5 songs each
-- =============================================

INSERT INTO album (id, title, artist_id, release_date) VALUES
-- Neon Pulse albums
(1, 'Grid City',         1, '2022-03-14'),
(2, 'Signal Decay',      1, '2024-01-20'),
-- Velvet Echo albums
(3, 'Cedar & Salt',      2, '2021-09-05'),
(4, 'The Quiet Season',  2, '2023-11-18'),
-- Drift Wave albums
(5, 'Afternoon Loops',   3, '2020-06-22'),
(6, 'Cassette Dreams',   3, '2023-04-08'),
-- Luna Skye albums
(7, 'Moonpool EP',       4, '2022-07-30'),
(8, 'Aphelion',          4, '2024-02-14');

-- Album songs
-- Grid City (album 1): songs 1-5
INSERT INTO album_song (album_id, song_id) VALUES
(1, 1),(1, 2),(1, 3),(1, 4),(1, 5),
-- Signal Decay (album 2): songs 6-10
(2, 6),(2, 7),(2, 8),(2, 9),(2, 10),
-- Cedar & Salt (album 3): songs 21-25
(3, 21),(3, 22),(3, 23),(3, 24),(3, 25),
-- The Quiet Season (album 4): songs 26-30
(4, 26),(4, 27),(4, 28),(4, 29),(4, 30),
-- Afternoon Loops (album 5): songs 41-45
(5, 41),(5, 42),(5, 43),(5, 44),(5, 45),
-- Cassette Dreams (album 6): songs 46-50
(6, 46),(6, 47),(6, 48),(6, 49),(6, 50),
-- Moonpool EP (album 7): songs 61-65
(7, 61),(7, 62),(7, 63),(7, 64),(7, 65),
-- Aphelion (album 8): songs 66-70
(8, 66),(8, 67),(8, 68),(8, 69),(8, 70);


-- =============================================
-- LIBRARIES — one per account
-- =============================================
INSERT INTO library (id, account_id) VALUES
(1, 1),(2, 2),(3, 3),(4, 4),(5, 5),
(6, 6),(7, 7),(8, 8),(9, 9);


-- =============================================
-- PLAYLISTS — 3-4 per user, public mix
-- =============================================
INSERT INTO playlist (id, title, creator_id, is_public) VALUES
-- John Doe (account 1) — 4 playlists
(1,  'Late Night Drive',         1, TRUE),
(2,  'Morning Focus',            1, TRUE),
(3,  'Rainy Day Mix',            1, FALSE),
(4,  'Gym Bangers',              1, FALSE),
-- Sarah M (account 2) — 3 playlists
(5,  'Folk & Chill',             2, TRUE),
(6,  'Autumn Feels',             2, TRUE),
(7,  'Study Playlist',           2, FALSE),
-- Mike B (account 3) — 4 playlists
(8,  'Synth Classics',           3, TRUE),
(9,  'Deep Focus',               3, FALSE),
(10, 'Weekend Vibes',            3, TRUE),
(11, 'Throwback Electro',        3, FALSE),
-- Nia W (account 4) — 3 playlists
(12, 'Dream Pop Essentials',     4, TRUE),
(13, 'Coffee Shop Sounds',       4, TRUE),
(14, 'Night Sky',                4, FALSE),
-- Carlos R (account 5) — 4 playlists
(15, 'Chill Beats Collection',   5, TRUE),
(16, 'Late Night Chill',         5, FALSE),
(17, 'Luna Favorites',           5, TRUE),
(18, 'Best of Everything',       5, FALSE);


-- =============================================
-- PLAYLIST ITEMS — 5-6 songs per playlist
-- =============================================

-- Playlist 1: Late Night Drive (synthwave heavy)
INSERT INTO playlist_item (playlist_id, song_id, position) VALUES
(1,  1,  1),(1,  4,  2),(1,  6,  3),(1,  9,  4),(1, 11,  5),(1, 18,  6),
-- Playlist 2: Morning Focus (ambient/lo-fi)
(2, 41,  1),(2, 50,  2),(2, 54,  3),(2, 61,  4),(2, 68,  5),(2, 74,  6),
-- Playlist 3: Rainy Day Mix (folk + dream pop)
(3, 22,  1),(3, 27,  2),(3, 30,  3),(3, 35,  4),(3, 63,  5),
-- Playlist 4: Gym Bangers (energetic electro)
(4,  2,  1),(4,  8,  2),(4, 10,  3),(4, 14,  4),(4, 16,  5),(4, 20,  6),
-- Playlist 5: Folk & Chill
(5, 21,  1),(5, 24,  2),(5, 26,  3),(5, 33,  4),(5, 37,  5),
-- Playlist 6: Autumn Feels
(6, 28,  1),(6, 30,  2),(6, 35,  3),(6, 39,  4),(6, 40,  5),(6, 63,  6),
-- Playlist 7: Study Playlist (lo-fi focused)
(7, 43,  1),(7, 44,  2),(7, 50,  3),(7, 52,  4),(7, 53,  5),(7, 59,  6),
-- Playlist 8: Synth Classics
(8,  1,  1),(8,  3,  2),(8,  7,  3),(8, 13,  4),(8, 17,  5),(8, 19,  6),
-- Playlist 9: Deep Focus
(9, 45,  1),(9, 47,  2),(9, 55,  3),(9, 69,  4),(9, 71,  5),
-- Playlist 10: Weekend Vibes
(10, 21, 1),(10, 41, 2),(10, 45, 3),(10, 61, 4),(10, 65, 5),(10, 75, 6),
-- Playlist 11: Throwback Electro
(11,  5, 1),(11,  9, 2),(11, 12, 3),(11, 15, 4),(11, 18, 5),
-- Playlist 12: Dream Pop Essentials
(12, 61, 1),(12, 63, 2),(12, 66, 3),(12, 72, 4),(12, 76, 5),(12, 79, 6),
-- Playlist 13: Coffee Shop Sounds
(13, 41, 1),(13, 43, 2),(13, 48, 3),(13, 54, 4),(13, 57, 5),
-- Playlist 14: Night Sky
(14, 64, 1),(14, 67, 2),(14, 70, 3),(14, 73, 4),(14, 78, 5),(14, 80, 6),
-- Playlist 15: Chill Beats Collection
(15, 42, 1),(15, 46, 2),(15, 51, 3),(15, 56, 4),(15, 58, 5),(15, 60, 6),
-- Playlist 16: Late Night Chill
(16,  1, 1),(16, 11, 2),(16, 62, 3),(16, 70, 4),(16, 74, 5),
-- Playlist 17: Luna Favorites
(17, 61, 1),(17, 64, 2),(17, 67, 3),(17, 71, 4),(17, 77, 5),(17, 80, 6),
-- Playlist 18: Best of Everything
(18,  4, 1),(18, 22, 2),(18, 44, 3),(18, 63, 4),(18, 16, 5),(18, 75, 6);


-- =============================================
-- LIBRARY SONGS (saved songs per user)
-- =============================================
INSERT INTO library_song (library_id, song_id) VALUES
-- John (lib 1)
(1, 1),(1, 4),(1, 11),(1, 18),(1, 61),(1, 74),
-- Sarah (lib 2)
(2, 22),(2, 27),(2, 35),(2, 40),(2, 63),
-- Mike (lib 3)
(3, 3),(3, 9),(3, 13),(3, 17),(3, 19),(3, 20),
-- Nia (lib 4)
(4, 62),(4, 66),(4, 72),(4, 76),(4, 78),(4, 80),
-- Carlos (lib 5)
(5, 41),(5, 50),(5, 56),(5, 58),(5, 60),(5, 75);

-- =============================================
-- LIBRARY ALBUMS
-- =============================================
INSERT INTO library_album (library_id, album_id) VALUES
(1, 1),(1, 7),
(2, 3),(2, 4),
(3, 1),(3, 2),
(4, 7),(4, 8),
(5, 5),(5, 6),(5, 8);

-- =============================================
-- LIBRARY PLAYLISTS (saved public playlists)
-- =============================================
INSERT INTO library_playlist (library_id, playlist_id) VALUES
(1, 5),(1, 12),
(2, 1),(2, 8),
(3, 12),(3, 15),
(4, 1),(4, 10),
(5, 6),(5, 8);

-- =============================================
-- LIBRARY ARTISTS (followed artists)
-- =============================================
INSERT INTO library_artist (library_id, artist_id) VALUES
(1, 1),(1, 4),
(2, 2),(2, 4),
(3, 1),(3, 2),(3, 3),
(4, 4),(4, 2),
(5, 3),(5, 4),(5, 1);

-- =============================================
-- ARTIST REQUESTS (some pending/approved/rejected)
-- =============================================
INSERT INTO artist_request (user_id, stage_name, reason, profile_picture_url, status, reviewed_at) VALUES
(1, 'J-Doe Beats',    'I produce electronic music and want to upload my EP.',    'https://cdn.tunix.io/req/jdoe.jpg',   'PENDING',  NULL),
(2, 'Echo Sarah',     'Singer-songwriter with 2 self-released albums.',           'https://cdn.tunix.io/req/sarah.jpg',  'APPROVED', '2024-11-10 14:32:00'),
(4, 'Nia Waves',      'Bedroom pop artist, active on SoundCloud with 10k plays.', 'https://cdn.tunix.io/req/niaw.jpg',   'REJECTED', '2024-10-05 09:15:00'),
(5, 'Carlos RMX',     'DJ and remixer, want to publish my original tracks.',       'https://cdn.tunix.io/req/carlos.jpg', 'PENDING',  NULL);