package tunix.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tunix.model.account.Artist;
import tunix.model.musicContent.Album;
import tunix.model.musicContent.Song;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponse {

    private Long id;
    private String title;
    private Long artistId;
    private LocalDate releaseDate;
    private List<Song> songs;
    
        public Album toAlbum() {

        Artist artist = new Artist(
                artistId,
                null,   // name not provided in response
                null,
                null,
                0,
                false
        );

        return new Album(
                title,
                id.intValue(),
                artist,
                new ArrayList<>(),
                releaseDate != null ? java.sql.Date.valueOf(releaseDate) : null
        );
    }
}