package tunixserver.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tunixserver.entities.AlbumEntity;

@Getter
@AllArgsConstructor
public class AlbumResponse {
    private Long albumId;
    private String title;
    private Long artistId;
    private LocalDateTime releaseDate;

    public static AlbumResponse fromAlbum(AlbumEntity album) {
        Long artistId = album.getArtist() != null ? album.getArtist().getArtistId() : null;

        return new AlbumResponse(
                album.getAlbumId(),
                album.getTitle(),
                artistId,
                album.getReleaseDate()
        );
    }
}
