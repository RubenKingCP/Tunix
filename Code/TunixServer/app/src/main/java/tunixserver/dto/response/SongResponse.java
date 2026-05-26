package tunixserver.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tunixserver.entities.ArtistEntity;
import tunixserver.entities.SongEntity; 

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SongResponse {

    private Long id;
    private String title;
    private ArtistEntity artistId;
    private String artistName;
    private int duration;
    private String filePathUrl;
    private String coverImageUrl;

    public static SongResponse fromEntity(SongEntity song) {
        return new SongResponse(
                song.getId(),
                song.getTitle(),
                song.getArtist() != null ? song.getArtist() : null,
                song.getArtist() != null ? song.getArtist().getAccount().getUsername() : null,
                song.getDuration(),
                song.getFilePathUrl(),
                song.getCoverImageUrl()
        );
    }
}