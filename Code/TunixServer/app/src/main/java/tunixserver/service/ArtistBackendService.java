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

    public ArtistResponse searchById(int artistId) {
        System.out.print("ArtistBackendService: Getting artist by id: " + artistId);
        ArtistEntity artistEntity = artistBackendRepository.findById(Long.valueOf(artistId))
                                .orElseThrow(() -> new RuntimeException("Couln't find artist by id"));

        return ArtistResponse.fromEntity(artistEntity);
    }
}
