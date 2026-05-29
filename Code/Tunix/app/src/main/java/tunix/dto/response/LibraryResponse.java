package tunix.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponse {

    private List<SongResponse> songs;
    private List<AlbumResponse> albums;
    private List<PlaylistResponse> playlists;
    private List<ArtistResponse> artists;

    public List<SongResponse> getSongs()         { return songs; }
    public List<AlbumResponse> getAlbums()       { return albums; }
    public List<PlaylistResponse> getPlaylists() { return playlists; }
    public List<ArtistResponse> getArtists()     { return artists; }

    // Semantic alias used by FollowService.isFollowing()
    public List<ArtistResponse> getFollowedArtists() {
        return artists;
    }
}