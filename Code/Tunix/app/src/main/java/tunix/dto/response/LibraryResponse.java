package tunix.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponse {

    private Long libraryId;
    private Long accountId;

    private List<SongResponse> songs;
    private List<AlbumResponse> albums;
    private List<PlaylistResponse> playlists;
    private List<ArtistResponse> artists;
}