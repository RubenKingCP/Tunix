package tunixserver.service;

import org.springframework.stereotype.Service;

import tunixserver.dto.request.AlbumRequest;
import tunixserver.entities.AlbumEntity;
import tunixserver.entities.ArtistEntity;

@Service
public class AlbumBackendService {

    public AlbumEntity uploadAlbum(AlbumRequest albumRequest) {
        ArtistEntity artist = null;

        if (albumRequest.artistId != null) {
            artist = ArtistEntity.builder()
                    .artistId(albumRequest.artistId)
                    .build();
        }

        return new AlbumEntity(
                albumRequest.title,
                artist,
                albumRequest.releaseDate
        );
    }

    public AlbumEntity fetchAlbum(AlbumRequest albumRequest) {
        if (albumRequest == null) {
            return null;
        }

        ArtistEntity artist = null;
        if (albumRequest.artistId != null) {
            artist = ArtistEntity.builder()
                    .artistId(albumRequest.artistId)
                    .build();
        }

        return new AlbumEntity(
                albumRequest.title,
                artist,
                albumRequest.releaseDate
        );
    }
}
