package tunixserver.dto.response;

import java.time.LocalDate;
import java.util.List;

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
    private List<SongResponse> songResponses;

    public static AlbumResponse fromEntity(AlbumEntity album) {
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