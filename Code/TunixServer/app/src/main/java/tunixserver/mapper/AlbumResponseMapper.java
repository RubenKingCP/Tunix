package tunixserver.mapper;

import tunixserver.dto.response.AlbumResponse;
import tunixserver.dto.response.SongResponse;
import tunixserver.entities.AlbumEntity;

public class AlbumResponseMapper {
        public static AlbumResponse fromEntity(AlbumEntity album) {

        if (album == null) return null;

        return new AlbumResponse(
                album.getId(),
                album.getTitle(),
                album.getArtist() != null ? album.getArtist().getId() : null,
                album.getReleaseDate(),
                album.getSongs().stream()
                        .map(SongResponse::fromEntity)
                        .toList()
        );
}
}
