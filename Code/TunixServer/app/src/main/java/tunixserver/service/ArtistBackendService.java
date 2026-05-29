package tunixserver.service;

import java.util.List;

import org.springframework.stereotype.Service;

import tunixserver.dto.response.ArtistResponse;
import tunixserver.entities.ArtistEntity;
import tunixserver.mapper.ArtistResponseMapper;
import tunixserver.repository.ArtistBackendRepository;

@Service
public class ArtistBackendService {
    private final ArtistBackendRepository artistBackendRepository;

    public ArtistBackendService(ArtistBackendRepository artistBackendRepository) {
        this.artistBackendRepository = artistBackendRepository;
    }
    
    public List<ArtistResponse> searchByName(String query) {

        List<ArtistEntity> artists = artistBackendRepository.findByDisplayNameContainingIgnoreCase(query);
        
        return artists.stream()
                .map(ArtistResponseMapper::fromEntity)
                .toList();
    }
}
