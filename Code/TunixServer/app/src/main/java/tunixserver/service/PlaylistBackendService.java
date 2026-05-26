package tunixserver.service;

import tunixserver.dto.request.PlaylistCreateRequest;
import tunixserver.entities.PlaylistEntity;
import tunixserver.entities.SongEntity;
import tunixserver.repository.PlaylistBackendRepository;
import tunixserver.repository.SongBackendRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistBackendService {
    private final PlaylistBackendRepository playlistBackendRepository;
    private final SongBackendRepository songBackendRepository;

    public PlaylistBackendService(PlaylistBackendRepository playlistBackendRepository, SongBackendRepository songBackendRepository) {
        this.playlistBackendRepository = playlistBackendRepository;
        this.songBackendRepository = songBackendRepository;
    }

    public boolean addSongToPlaylist(Long playlistId, Long songId) {

        PlaylistEntity playlist = playlistBackendRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        SongEntity song = songBackendRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found"));

        // check duplicate (based on ManyToMany list)
        boolean exists = playlist.getSongs()
                .stream()
                .anyMatch(s -> s.getSongId().equals(songId));

        if (exists) {
            return false;
        }

        playlist.getSongs().add(song);

        playlistBackendRepository.save(playlist);

        return true;
    }

    public boolean checkDuplicate(Long playlistId, Long songId) {
        PlaylistEntity playlist = playlistBackendRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        for (SongEntity s : playlist.getSongs()) {
            if (s.getSongId() == songId) {
                return true; // Song already in playlist
            }
        }
        return false; // Song not in playlist
    } 

    public PlaylistEntity createPlaylist(PlaylistCreateRequest playlistCreateRequest) {
        // Code for this
        // PlaylistEntity playlistEntity = playlistCreateRequest.toPlaylistEntity();
        // playlistBackendRepository.save(playlistEntity);
        return null;
    }
}
