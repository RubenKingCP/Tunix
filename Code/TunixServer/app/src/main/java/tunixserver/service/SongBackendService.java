package tunixserver.service;

import tunixserver.dto.request.SongRequest;
import tunixserver.entities.SongEntity;
import tunixserver.repository.SongBackendRepository;

public class SongBackendService {
    private final SongBackendRepository songRepository;
    
    public SongBackendService(SongBackendRepository songRepository) {
        // Initialize any required resources, such as repositories or services
        this.songRepository = songRepository;
    }
    public SongEntity uploadSong(SongRequest songFormDTO) {
        // Logic to save the song to the database
        // Check for duplicates
        if (songRepository.existsByTitleAndArtistId(songFormDTO.getTitle(), songFormDTO.getArtistId())) {
            throw new RuntimeException("Song already exists");
        }
        // This is a placeholder implementation
        return null; // Replace with actual logic to save and return the song entity
    }
}
