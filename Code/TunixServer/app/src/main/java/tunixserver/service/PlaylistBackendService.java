package tunixserver.service;

import tunixserver.dto.request.PlaylistCreateRequest;
import tunixserver.entities.AccountEntity;
import tunixserver.entities.PlaylistEntity;
import tunixserver.entities.SongEntity;
import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.PlaylistBackendRepository;
import tunixserver.repository.SongBackendRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PlaylistBackendService {
    private final PlaylistBackendRepository playlistBackendRepository;
    private final SongBackendRepository songBackendRepository;
    private final AccountBackendRepository accountBackendRepository;

    public PlaylistBackendService(AccountBackendRepository accountBackendRepository, PlaylistBackendRepository playlistBackendRepository, SongBackendRepository songBackendRepository) {
        this.playlistBackendRepository = playlistBackendRepository;
        this.songBackendRepository = songBackendRepository;
        this.accountBackendRepository = accountBackendRepository;
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

    public PlaylistEntity createPlaylist(PlaylistCreateRequest request) {

        AccountEntity creator = accountBackendRepository.findById(request.getCreatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        List<AccountEntity> coauthors = new ArrayList<>();

        if (request.getCoauthorIds() != null && !request.getCoauthorIds().isEmpty()) {
            coauthors = accountBackendRepository.findAllById(request.getCoauthorIds());
        }

        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setTitle(request.getTitle());
        playlist.setCreator(creator);
        playlist.setPublic(false);
        playlist.setCreatedAt(LocalDateTime.now());
        playlist.setUpdatedAt(LocalDateTime.now());
        playlist.setCoauthors(coauthors);

        return playlistBackendRepository.save(playlist);
    }
}
