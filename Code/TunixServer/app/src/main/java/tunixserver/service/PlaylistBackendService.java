package tunixserver.service;

import tunixserver.dto.request.PlaylistCreateRequest;
import tunixserver.entities.PlaylistEntity;
import tunixserver.entities.SongEntity;
import tunixserver.repository.PlaylistBackendRepository;
import tunixserver.repository.SongBackendRepository;

public class PlaylistBackendService {
    private final PlaylistBackendRepository playlistBackendRepository;
    private final SongBackendRepository songBackendRepository;

    public PlaylistBackendService(PlaylistBackendRepository playlistBackendRepository, SongBackendRepository songBackendRepository) {
        this.playlistBackendRepository = playlistBackendRepository;
        this.songBackendRepository = songBackendRepository;
    }

    public boolean addSongToPlaylist(int playlistId, int songId) {
        // Logic to add the song to the playlist in the database
        PlaylistEntity playlist = playlistBackendRepository.findById(playlistId);
        SongEntity song = songBackendRepository.findById(songId).orElseThrow(null);
        // Check if already in playlist, if not add to playlist and save
        if (!checkDuplicate(playlistId, songId)) {
            playlist.addSong(song);
            playlistBackendRepository.save(playlist);
        } else {
            // Return error response to controller indicating the song is already in the playlist
            return false;
        }
        return true;
    }

    public boolean checkDuplicate(int playlistId, int songId) {
        PlaylistEntity playlist = playlistBackendRepository.findById(playlistId);
        for (SongEntity s : playlist.getSongs()) {
            if (s.getId() == songId) {
                return true; // Song already in playlist
            }
        }
        return false; // Song not in playlist
    } 

    public boolean createPlaylist(PlaylistCreateRequest playlistCreateRequest) {
        // Code for this
        // PlaylistEntity playlistEntity = playlistCreateRequest.toPlaylistEntity();
        // playlistBackendRepository.save(playlistEntity);
        return false;
    }
}
