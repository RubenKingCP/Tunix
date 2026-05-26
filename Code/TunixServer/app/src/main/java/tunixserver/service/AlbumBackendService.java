package tunixserver.service;

import org.springframework.stereotype.Service;
import tunixserver.dto.request.AlbumRequest;
import tunixserver.entities.AlbumEntity;
import tunixserver.entities.ArtistEntity;
import tunixserver.repository.ArtistBackendRepository;

@Service
public class AlbumBackendService {

    private final ArtistBackendRepository artistRepository;

    public AlbumBackendService(ArtistBackendRepository artistRepository) {
        this.artistRepository = artistRepository;
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
}