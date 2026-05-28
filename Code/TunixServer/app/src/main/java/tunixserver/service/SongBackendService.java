package tunixserver.service;

import tunixserver.dto.request.SongRequest;
import tunixserver.dto.response.SongResponse;
import tunixserver.entities.ArtistEntity;
import tunixserver.entities.SongEntity;
import tunixserver.mapper.SongMapper;
import tunixserver.repository.ArtistBackendRepository;
import tunixserver.repository.SongBackendRepository;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

@Service
public class SongBackendService {
    private final SongBackendRepository songRepository;
    private final ArtistBackendRepository artistRepository;
    
    public SongBackendService(SongBackendRepository songRepository, ArtistBackendRepository artistRepository) {
        // Initialize any required resources, such as repositories or services
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }
    public SongEntity uploadSong(SongRequest req) {
        System.out.println("SongBackendService: Reached upload song");
        // 1. Check duplicate
        boolean exists = songRepository.existsByTitleAndArtist_Id(
                req.getTitle(),
                req.getArtistId()
        );
        System.out.println("Duplicate Song found: " + exists);

        if (exists) {
            throw new RuntimeException("Song already exists");
        }

        // 2. Fetch artist
        System.out.println("Fetching Song's Artist");
        ArtistEntity artist = artistRepository.findById(req.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        // 3. Create song entity
        SongEntity song = new SongEntity();
        song.setTitle(req.getTitle());
        song.setArtist(artist);
        song.setDuration(req.getDuration());
        song.setFilePathUrl(req.getFilePathUrl());
        song.setCoverImageUrl(req.getCoverImageUrl());

        // 4. Save to DB
        return songRepository.save(song);
    }

    public List<SongResponse> getAllSongs() {
        System.out.println("SongBackendService: Reached get all songs");
        List<SongEntity> songs = songRepository.findAll();

        if(songs.isEmpty()) {
            return List.of(); 
        } 

        return songs.stream()
                .map(SongMapper::toResponse)
                .toList();
    }

        public List<SongResponse> searchByName(String query) {

        List<SongEntity> songs = songRepository.findByTitleContainingIgnoreCase(query);

        return songs.stream()
                .map(SongMapper::toResponse)
                .toList();
    }

    public void deleteSong(Long id) {

        if (!songRepository.existsById(id)) {
            throw new RuntimeException("Song not found");
        }

        songRepository.deleteById(id);
    }
}
