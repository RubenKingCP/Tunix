package tunixserver.service;

import tunixserver.dto.request.PlaylistCreateRequest;
import tunixserver.dto.response.PlaylistResponse;
import tunixserver.entities.AccountEntity;
import tunixserver.entities.LibraryEntity;
import tunixserver.entities.LibraryPlaylistEntity;
import tunixserver.entities.PlaylistEntity;
import tunixserver.entities.SongEntity;
import tunixserver.mapper.PlaylistResponseMapper;
import tunixserver.repository.AccountBackendRepository;
import tunixserver.repository.LibraryBackendRepository;
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
    private final LibraryBackendRepository libraryBackendRepository;

    public PlaylistBackendService(AccountBackendRepository accountBackendRepository, PlaylistBackendRepository playlistBackendRepository, SongBackendRepository songBackendRepository, LibraryBackendRepository libraryBackendRepository) {
        this.playlistBackendRepository = playlistBackendRepository;
        this.songBackendRepository = songBackendRepository;
        this.accountBackendRepository = accountBackendRepository;
        this.libraryBackendRepository = libraryBackendRepository;
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

        //CHECK DUPLICATE NAME FOR SAME CREATOR
        playlistBackendRepository.findByTitleAndCreator_AccountId(
                request.getTitle(),
                request.getCreatorId()
        ).ifPresent(p -> {
            throw new RuntimeException("Playlist with this name already exists");
        });


        // Create playlist and svae to database
        List<AccountEntity> coauthors = new ArrayList<>();

        PlaylistEntity playlist = new PlaylistEntity();
        playlist.setTitle(request.getTitle());
        playlist.setCreator(creator);
        playlist.setPublic(false);
        playlist.setCreatedAt(LocalDateTime.now());
        playlist.setUpdatedAt(LocalDateTime.now());
        playlist.setCoauthors(coauthors);
        playlistBackendRepository.save(playlist);
        
        // Add to user library
        LibraryEntity library= libraryBackendRepository
                .findByAccount_AccountId(request.getCreatorId())
                .orElseThrow(()-> new RuntimeException("Library Not Found"));
        
                LibraryPlaylistEntity libraryPlaylistEntity = new LibraryPlaylistEntity();

                libraryPlaylistEntity.setLibrary(library);
                libraryPlaylistEntity.setPlaylist(playlist);

                library.getPlaylists().add(libraryPlaylistEntity);

                libraryBackendRepository.save(library);

        return playlist;
    }

    public List<PlaylistResponse> searchByName(String queryString) {

        List<PlaylistEntity> playlists = playlistBackendRepository.findByTitleContainingIgnoreCase(queryString);

        return playlists.stream()
                        .map(PlaylistResponseMapper::fromEntity)
                        .toList();
    }
    
    public PlaylistResponse getPlaylist(Long playlistId) {
        PlaylistEntity playlistEntity = playlistBackendRepository.findById(playlistId)
                                .orElseThrow(() -> new RuntimeException("Playlist with this id coulnt be found"));
        return PlaylistResponseMapper.fromEntity(playlistEntity);
    }
}
