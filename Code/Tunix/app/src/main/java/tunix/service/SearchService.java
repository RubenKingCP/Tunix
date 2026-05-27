package tunix.service;

import java.sql.Date;
import java.util.List;

import tunix.api.AlbumApi;
import tunix.api.ArtistApi;
import tunix.api.PlaylistApiClient;
import tunix.api.SongApiClient;
import tunix.model.ILibraryAsset;
import tunix.model.account.Artist;
import tunix.model.musicContent.Album;
import tunix.model.musicContent.Playlist;
import tunix.model.musicContent.Song;

public class SearchService {
    private final SongApiClient songApiClient;
    private final PlaylistApiClient playlistApiClient;
    private final AlbumApi albumApi;
    private final ArtistApi artistApi;

    public SearchService(SongApiClient songApiClient, PlaylistApiClient playlistApiClient, AlbumApi albumApi, ArtistApi artistApi) {
        this.playlistApiClient = playlistApiClient;
        this.songApiClient = songApiClient;
        this.albumApi = albumApi;
        this.artistApi = artistApi;
    }

    public List<ILibraryAsset> search(String query, String type) {
        String normalizedType = type == null ? "" : type.trim().toLowerCase();

        if ("song".equals(normalizedType)) {
            // ApiResponse<SongResponse> response = songApiClient.getSongsByName(query);
            // if (response.isSuccess() && response.getData() != null) {
            //     return List.of(response.getData().toSong());
            // }
            // return List.of();
            return List.of(new Song(
                    "Dummy Song",
                    1L,
                    new Artist(1L, "Dummy Artist", "dummy.artist@example.com", "Dummy artist bio", 0, false),
                    180,
                    "",
                    ""));
        }

        if ("playlist".equals(normalizedType)) {
            // playlistApiClient.getPlaylistsByName(query);
            // return List.of();
            return List.of(new Playlist(
                    "Dummy Playlist",
                    1,
                    new Artist(2L, "Dummy Creator", "dummy.creator@example.com", "Dummy creator bio", 0, false)));
        }

        if ("artist".equals(normalizedType)) {
            String artistName = query == null || query.trim().isEmpty()
                    ? "Dummy Artist"
                    : query.trim();

            return List.of(new Artist(
                    10L,
                    artistName,
                    "artist@example.com",
                    "Artist search result for " + artistName + ".",
                    1200,
                    true));
        }

        if ("album".equals(normalizedType)) {
            // return List.of();
            Artist artist = new Artist(3L, "Dummy Album Artist", "dummy.album.artist@example.com", "Dummy album artist bio", 0, false);
            Song dummySong = new Song(
                    "Dummy Album Song",
                    2l,
                    artist,
                    200,
                    "",
                    "");
            Album dummyAlbum = new Album(
                    "Dummy Album",
                    1,
                    artist,
                    List.of(dummySong),
                    Date.valueOf("2024-01-01"));
            return List.of(dummyAlbum);
        }

        System.err.println("Search for this type has not been implemented yet");
        return List.of();
    }
}
