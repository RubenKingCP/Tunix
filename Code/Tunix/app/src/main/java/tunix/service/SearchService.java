package tunix.service;

import java.sql.Date;
import java.util.List;

import org.checkerframework.checker.units.qual.s;

import tunix.api.AlbumApi;
import tunix.api.PlaylistApiClient;
import tunix.api.SongApiClient;
import tunix.dto.response.AlbumResponse;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.PlaylistResponse;
import tunix.dto.response.SongResponse;
import tunix.model.ILibraryAsset;
import tunix.model.account.Account;
import tunix.model.account.Artist;
import tunix.model.account.User;
import tunix.model.musicContent.Album;
import tunix.model.musicContent.Playlist;
import tunix.model.musicContent.Song;

public class SearchService {
    private final SongApiClient songApiClient;
    private final PlaylistApiClient playlistApiClient;
    private final AlbumApi albumApiClient;

    public SearchService(SongApiClient songApiClient, PlaylistApiClient playlistApiClient, AlbumApi albumApi) {
        this.playlistApiClient = playlistApiClient;
        this.songApiClient = songApiClient;
        this.albumApiClient = albumApi;
    }

    public List<ILibraryAsset> search(String query, String type) {
        System.out.println("SearchService: Reached search query: " + query + "\nSearchType: " + type);
        String normalizedType = type == null ? "" : type.trim().toLowerCase();

        try {
            switch (normalizedType) {

                case "song": {
                    ApiResponse<List<SongResponse>> response =
                            songApiClient.getSongsByName(query);

                    if (response != null && response.isSuccess() && response.getData() != null) {
                    return response.getData()
                                .stream()
                                .map(sr -> toSong(sr))
                                .toList();
                    }
                    return List.of();
                }
                case "playlist": {
                    ApiResponse<List<PlaylistResponse>> response =
                                    playlistApiClient.getPlaylistsByName(query);
                    
                    if(response != null && response.isSuccess() && response.getData() != null) {
                        return response.getData()
                                    .stream()
                                    .map(sr -> toPlaylist(sr))
                                    .toList();
                    }
                    return List.of();
                }

                case "artist": {
                    // artistApiClient.getArtistsByName(query);
                    return List.of();
                }

                case "album": {
                    ApiResponse<List<AlbumResponse>> response = albumApiClient.getAlbumsByName(query);
                    if (response != null && response.isSuccess() && response.getData() != null) {
                        return response.getData()
                                    .stream()
                                    .map(sr -> toAlbum(sr))
                                    .toList();
                        }
                    }
                    return List.of();
                default:
                    return List.of();
            }

        } catch (Exception e) {
            System.err.println("Search failed for type=" + normalizedType + ", query=" + query);
            e.printStackTrace();

            // =========================
            // FALLBACK DUMMY DATA
            // =========================
            switch (normalizedType) {

                case "song":
                    return List.of(new Song(
                            "Dummy Song",
                            1L,
                            new Artist(1L, "Dummy Artist", "dummy.artist@example.com", "Dummy artist bio", 0, false),
                            180,
                            "",
                            ""
                    ));

                case "playlist":
                    return List.of(new Playlist(
                            "Dummy Playlist",
                            1,
                            new Artist(2L, "Dummy Creator", "dummy.creator@example.com", "Dummy creator bio", 0, false)
                    ));

                case "artist": {
                    String artistName = (query == null || query.trim().isEmpty())
                            ? "Dummy Artist"
                            : query.trim();

                    return List.of(new Artist(
                            10L,
                            artistName,
                            "artist@example.com",
                            "Artist search result for " + artistName + ".",
                            1200,
                            true
                    ));
                }

                case "album": {
                    Artist artist = new Artist(
                            3L,
                            "Dummy Album Artist",
                            "dummy.album.artist@example.com",
                            "Dummy album artist bio",
                            0,
                            false
                    );

                    Song dummySong = new Song(
                            "Dummy Album Song",
                            2L,
                            artist,
                            200,
                            "",
                            ""
                    );

                    Album dummyAlbum = new Album(
                            "Dummy Album",
                            1,
                            artist,
                            List.of(dummySong),
                            Date.valueOf("2024-01-01")
                    );

                    return List.of(dummyAlbum);
                }

                default:
                    return List.of();
            }
        }
    }

    public ILibraryAsset toSong(SongResponse songResponse) {
        return new Song(songResponse.getTitle(), songResponse.getId(), new Artist(songResponse.getArtistId(), songResponse.getArtistName(), null, null, 0, false), songResponse.getDuration(), songResponse.getFilePathUrl(), null);
    }

    public ILibraryAsset toAlbum(AlbumResponse albumResponse){
        return new Album(
            albumResponse.getTitle(), albumResponse.getId().intValue(), new Artist(albumResponse.getArtistId(), null, null, null, 0, false), null, null);
    }

    public ILibraryAsset toPlaylist(PlaylistResponse playlistResponse) {
        return new Playlist(playlistResponse.getTitle(), playlistResponse.getId().intValue(), new Artist(playlistResponse.getCreatorId(), playlistResponse.getCreatorName(), null, null, 0, false));
    }
}
