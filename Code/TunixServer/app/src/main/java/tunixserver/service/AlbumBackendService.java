package tunixserver.service;

import java.util.List;

import org.springframework.stereotype.Service;
import tunixserver.dto.request.AlbumRequest;
import tunixserver.dto.response.AlbumResponse;
import tunixserver.entities.AlbumEntity;
import tunixserver.entities.ArtistEntity;
import tunixserver.mapper.AlbumResponseMapper;
import tunixserver.repository.AlbumBackendRepository;
import tunixserver.repository.ArtistBackendRepository;

@Service
public class AlbumBackendService {

    private final ArtistBackendRepository artistRepository;
    private final AlbumBackendRepository albumBackendRepository;

    public AlbumBackendService(ArtistBackendRepository artistRepository, AlbumBackendRepository albumBackendRepository) {
        this.artistRepository = artistRepository;
        this.albumBackendRepository = albumBackendRepository;
    }

    public AlbumEntity uploadAlbum(AlbumRequest req) {

        ArtistEntity artist = artistRepository.findById(req.getArtistId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        return new AlbumEntity(
                req.getTitle(),
                artist,
                req.getReleaseDate()
        );
    }

    public AlbumEntity fetchAlbum(AlbumRequest req) {
        if (req == null) return null;

        ArtistEntity artist = null;

        if (req.getArtistId() != null) {
            artist = artistRepository.findById(req.getArtistId())
                    .orElse(null);
        }

        return new AlbumEntity(
                req.getTitle(),
                artist,
                req.getReleaseDate()
        );
    }

        public List<AlbumResponse> searchByName(String query) {

            List<AlbumEntity> albums = albumBackendRepository.findByTitleContainingIgnoreCase(query);

            return albums.stream()
                    .map(AlbumResponseMapper::fromEntity)
                    .toList();
        }

        public AlbumResponse getAlbumById(Long albumId) {
            return AlbumResponse.fromEntity(albumBackendRepository.findById(albumId).orElseThrow(() -> new RuntimeException("Coulnt find album!")));
        }
}