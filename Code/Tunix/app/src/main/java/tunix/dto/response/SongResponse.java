package tunix.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tunix.model.account.Artist;
import tunix.model.musicContent.Song;

@Getter
@Setter
@NoArgsConstructor
public class SongResponse {

    private Long id;

    private String title;

    private Long artistId;

    private String artistName;

    private int duration;

    private String filePathUrl;

    private String coverImageUrl;

    public Song toSong() {

        Artist artist = new Artist(
                artistId,
                artistName,
                null,
                null,
                0,
                false
        );

        return new Song(
                title,
                id,
                artist,
                duration,
                filePathUrl,
                coverImageUrl
        );
    }
}