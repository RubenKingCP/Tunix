use tunix;

-- =========================
-- ACCOUNTS
-- =========================

INSERT INTO account (username, email, password, role) VALUES
('user1', 'user1@example.com', 'pass123', 'USER'),
('user2', 'user2@example.com', 'pass123', 'USER'),
('user3', 'user3@example.com', 'pass123', 'USER'),
('user4', 'user4@example.com', 'pass123', 'USER'),
('user5', 'user5@example.com', 'pass123', 'USER'),

('artist1', 'artist1@example.com', 'pass123', 'ARTIST'),
('artist2', 'artist2@example.com', 'pass123', 'ARTIST'),
('artist3', 'artist3@example.com', 'pass123', 'ARTIST'),
('artist4', 'artist4@example.com', 'pass123', 'ARTIST'),
('artist5', 'artist5@example.com', 'pass123', 'ARTIST'),

('admin1', 'admin1@example.com', 'admin123', 'ADMIN');

-- =========================
-- USERS
-- =========================

INSERT INTO user (
    account_id,
    display_name,
    profile_picture_url,
    premium,
    premium_trial_used,
    downloaded_songs_count
) VALUES
(1, 'John Smith', 'https://picsum.photos/200?1', TRUE, TRUE, 24),
(2, 'Alice Johnson', 'https://picsum.photos/200?2', FALSE, FALSE, 3),
(3, 'Michael Brown', 'https://picsum.photos/200?3', TRUE, TRUE, 52),
(4, 'Emma Wilson', 'https://picsum.photos/200?4', FALSE, TRUE, 7),
(5, 'David Miller', 'https://picsum.photos/200?5', TRUE, FALSE, 15);

-- =========================
-- ARTISTS
-- =========================

INSERT INTO artist (
    account_id,
    biography,
    followers_count,
    verified,
    display_name
) VALUES
(6, 'Electronic music producer from Berlin.', 1200, TRUE, "ElectricNights"),
(7, 'Indie pop vocalist and songwriter.', 890, TRUE, "IndianSupra"),
(8, 'Hip hop artist and beat maker.', 450, FALSE, "MpampisOSougis"),
(9, 'Lo-fi ambient music creator.', 2000, TRUE, "LaserKing"),
(10, 'Experimental synthwave producer.', 670, FALSE, "Steve");

-- =========================
-- ADMIN
-- =========================

INSERT INTO admin (account_id) VALUES
(11);

-- =========================
-- SONGS (10 PER ARTIST)
-- =========================

INSERT INTO song (
    title,
    artist_id,
    duration,
    file_path_url,
    cover_image_url
) VALUES

-- ARTIST 1
('Neon Nights', 1, 210, '/songs/neon_nights.mp3', '/covers/neon_nights.jpg'),
('Digital Dreams', 1, 198, '/songs/digital_dreams.mp3', '/covers/digital_dreams.jpg'),
('Urban Pulse', 1, 220, '/songs/urban_pulse.mp3', '/covers/urban_pulse.jpg'),
('Skyline Echo', 1, 240, '/songs/skyline_echo.mp3', '/covers/skyline_echo.jpg'),
('Midnight Drive', 1, 205, '/songs/midnight_drive.mp3', '/covers/midnight_drive.jpg'),
('Future Waves', 1, 215, '/songs/future_waves.mp3', '/covers/future_waves.jpg'),
('Crystal Lights', 1, 199, '/songs/crystal_lights.mp3', '/covers/crystal_lights.jpg'),
('Electric City', 1, 221, '/songs/electric_city.mp3', '/covers/electric_city.jpg'),
('Afterglow', 1, 212, '/songs/afterglow.mp3', '/covers/afterglow.jpg'),
('Night Runner', 1, 225, '/songs/night_runner.mp3', '/covers/night_runner.jpg'),

-- ARTIST 2
('Golden Hour', 2, 180, '/songs/golden_hour.mp3', '/covers/golden_hour.jpg'),
('Soft Rain', 2, 176, '/songs/soft_rain.mp3', '/covers/soft_rain.jpg'),
('Heartstrings', 2, 190, '/songs/heartstrings.mp3', '/covers/heartstrings.jpg'),
('Wanderlust', 2, 201, '/songs/wanderlust.mp3', '/covers/wanderlust.jpg'),
('Fading Lights', 2, 184, '/songs/fading_lights.mp3', '/covers/fading_lights.jpg'),
('Wildflowers', 2, 192, '/songs/wildflowers.mp3', '/covers/wildflowers.jpg'),
('Moonlit Sky', 2, 200, '/songs/moonlit_sky.mp3', '/covers/moonlit_sky.jpg'),
('Lost Again', 2, 187, '/songs/lost_again.mp3', '/covers/lost_again.jpg'),
('Blue Horizon', 2, 194, '/songs/blue_horizon.mp3', '/covers/blue_horizon.jpg'),
('Ocean Eyes', 2, 183, '/songs/ocean_eyes.mp3', '/covers/ocean_eyes.jpg'),

-- ARTIST 3
('Street Kings', 3, 230, '/songs/street_kings.mp3', '/covers/street_kings.jpg'),
('808 Dreams', 3, 240, '/songs/808_dreams.mp3', '/covers/808_dreams.jpg'),
('Trap Vision', 3, 220, '/songs/trap_vision.mp3', '/covers/trap_vision.jpg'),
('Late Night Flow', 3, 210, '/songs/late_night_flow.mp3', '/covers/late_night_flow.jpg'),
('Concrete Jungle', 3, 225, '/songs/concrete_jungle.mp3', '/covers/concrete_jungle.jpg'),
('Bassline Heat', 3, 215, '/songs/bassline_heat.mp3', '/covers/bassline_heat.jpg'),
('Dark Mode', 3, 205, '/songs/dark_mode.mp3', '/covers/dark_mode.jpg'),
('Shadow Run', 3, 199, '/songs/shadow_run.mp3', '/covers/shadow_run.jpg'),
('Night Shift', 3, 207, '/songs/night_shift.mp3', '/covers/night_shift.jpg'),
('Lowkey', 3, 198, '/songs/lowkey.mp3', '/covers/lowkey.jpg'),

-- ARTIST 4
('Dreamscape', 4, 260, '/songs/dreamscape.mp3', '/covers/dreamscape.jpg'),
('Floating Away', 4, 245, '/songs/floating_away.mp3', '/covers/floating_away.jpg'),
('Cloud Memory', 4, 250, '/songs/cloud_memory.mp3', '/covers/cloud_memory.jpg'),
('Silent Room', 4, 270, '/songs/silent_room.mp3', '/covers/silent_room.jpg'),
('Distant Stars', 4, 255, '/songs/distant_stars.mp3', '/covers/distant_stars.jpg'),
('Deep Focus', 4, 240, '/songs/deep_focus.mp3', '/covers/deep_focus.jpg'),
('Night Air', 4, 235, '/songs/night_air.mp3', '/covers/night_air.jpg'),
('Hidden Path', 4, 248, '/songs/hidden_path.mp3', '/covers/hidden_path.jpg'),
('Soft Static', 4, 252, '/songs/soft_static.mp3', '/covers/soft_static.jpg'),
('Aurora', 4, 265, '/songs/aurora.mp3', '/covers/aurora.jpg'),

-- ARTIST 5
('Retro Vision', 5, 210, '/songs/retro_vision.mp3', '/covers/retro_vision.jpg'),
('Synth Runner', 5, 225, '/songs/synth_runner.mp3', '/covers/synth_runner.jpg'),
('Laser Dreams', 5, 215, '/songs/laser_dreams.mp3', '/covers/laser_dreams.jpg'),
('Future City', 5, 205, '/songs/future_city.mp3', '/covers/future_city.jpg'),
('Arcade Love', 5, 198, '/songs/arcade_love.mp3', '/covers/arcade_love.jpg'),
('Cyber Sunset', 5, 220, '/songs/cyber_sunset.mp3', '/covers/cyber_sunset.jpg'),
('Pixel Sky', 5, 214, '/songs/pixel_sky.mp3', '/covers/pixel_sky.jpg'),
('Electric Horizon', 5, 230, '/songs/electric_horizon.mp3', '/covers/electric_horizon.jpg'),
('Chrome Nights', 5, 222, '/songs/chrome_nights.mp3', '/covers/chrome_nights.jpg'),
('Analog Heart', 5, 208, '/songs/analog_heart.mp3', '/covers/analog_heart.jpg');

-- =========================
-- ALBUMS (10)
-- =========================

INSERT INTO album (
    title,
    artist_id,
    release_date
) VALUES
('Neon Future', 1, '2024-01-10'),
('Electric Dreams', 1, '2024-02-15'),
('Golden Skies', 2, '2024-03-01'),
('Lost Signals', 2, '2024-03-20'),
('Trap Kingdom', 3, '2024-04-11'),
('Bassline City', 3, '2024-04-25'),
('Dreamscape', 4, '2024-05-07'),
('Ambient Nights', 4, '2024-05-21'),
('Retro Future', 5, '2024-06-15'),
('Cyber Waves', 5, '2024-07-01');

-- =========================
-- PLAYLISTS (10)
-- =========================

INSERT INTO playlist (
    title,
    creator_id,
    is_public
) VALUES
('Workout Mix', 1, TRUE),
('Late Night Coding', 1, TRUE),
('Chill Vibes', 2, TRUE),
('Road Trip', 2, FALSE),
('Focus Session', 3, TRUE),
('LoFi Nights', 3, TRUE),
('Gaming Playlist', 4, TRUE),
('Synthwave Classics', 4, FALSE),
('Daily Mix', 5, TRUE),
('Relaxation', 5, TRUE);

-- =========================
-- PLAYLIST ITEMS
-- =========================

INSERT INTO playlist_item (
    playlist_id,
    song_id,
    position
) VALUES
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),

(2, 11, 1),
(2, 12, 2),
(2, 13, 3),

(3, 21, 1),
(3, 22, 2),
(3, 23, 3),

(4, 31, 1),
(4, 32, 2),
(4, 33, 3),

(5, 41, 1),
(5, 42, 2),
(5, 43, 3);

-- =========================
-- LIBRARIES
-- =========================

INSERT INTO library (account_id) VALUES
(1),
(2),
(3),
(4),
(5);

-- =========================
-- LIBRARY SONGS
-- =========================

INSERT INTO library_song (library_id, song_id) VALUES
(1, 1),
(1, 5),
(1, 10),

(2, 11),
(2, 15),
(2, 18),

(3, 21),
(3, 25),

(4, 31),
(4, 35),

(5, 41),
(5, 45);

-- =========================
-- LIBRARY ALBUMS
-- =========================

INSERT INTO library_album (library_id, album_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5),
(3, 6),
(4, 7),
(4, 8),
(5, 9),
(5, 10);

-- =========================
-- LIBRARY PLAYLISTS
-- =========================

INSERT INTO library_playlist (library_id, playlist_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5),
(3, 6),
(4, 7),
(4, 8),
(5, 9),
(5, 10);

-- =========================
-- LIBRARY ARTISTS
-- =========================

INSERT INTO library_artist (library_id, artist_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5),
(4, 1),
(5, 2);

