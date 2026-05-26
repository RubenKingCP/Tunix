package tunixserver.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tunixserver.entities.AlbumEntity;

@Getter
@AllArgsConstructor
public class AlbumResponse {

    private Long id;
    private String title;
    private Long artistId;
    private LocalDate releaseDate;

    public static AlbumResponse fromEntity(AlbumEntity album) {
        return new AlbumResponse(
                album.getId(),
                album.getTitle(),
                album.getArtist() != null ? album.getArtist().getId() : null,
                album.getReleaseDate()
        );
    }
}