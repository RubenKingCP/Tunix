package tunixserver.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import tunixserver.entities.LibraryEntity;

@Getter
@Setter
public class LibraryResponse {

    private List<SongResponse> songs;
    private List<AlbumResponse> albums;
    private List<PlaylistResponse> playlists;
    private List<ArtistResponse> artists;

    public LibraryResponse() {}

    public LibraryResponse(
            List<SongResponse> songs,
            List<AlbumResponse> albums,
            List<PlaylistResponse> playlists,
            List<ArtistResponse> artists
    ) {
        this.songs = songs;
        this.albums = albums;
        this.playlists = playlists;
        this.artists = artists;
    }

    public static LibraryResponse fromEntity(LibraryEntity library) {
        System.out.println("Sending library reponse");
        return new LibraryResponse(

                library.getSongs()
                        .stream()
                        .map(ls -> SongResponse.fromEntity(ls.getSong()))
                        .toList(),

                library.getAlbums()
                        .stream()
                        .map(la -> AlbumResponse.fromEntity(la.getAlbum()))
                        .toList(),

                library.getPlaylists()
                        .stream()
                        .map(lp -> PlaylistResponse.fromEntity(lp.getPlaylist()))
                        .toList(),

                library.getArtists()
                        .stream()
                        .map(la -> ArtistResponse.fromEntity(la.getArtist()))
                        .toList()
        );
    }
}