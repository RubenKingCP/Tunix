package tunix.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import tunix.model.ILibraryAsset;
import tunix.model.account.Account;
import tunix.service.auth.SessionService;
import tunix.api.*;

public class LibraryService {
        private final AlbumApi albumApiClient;
        private final ArtistApi artistApiClient;
        private final PlaylistApiClient playlistApiClient;
        public LibraryService(AlbumApi albumApiClient, ArtistApi artistApiClient, PlaylistApiClient playlistApiClient) {
            this.albumApiClient = albumApiClient;
            this.artistApiClient = artistApiClient;
            this.playlistApiClient = playlistApiClient;
        }
    public List<ILibraryAsset> getLibraryAssets() {
        Account you = SessionService.Instance.getAccount();
        if (you == null) {
            return new ArrayList<>();
        }
        
        List<ILibraryAsset> assets = new ArrayList<>();
        // Fetch songs, albums, artists, and playlists from the API
        List<ILibraryAsset> playlists = playlistApiClient.getUserPlaylists(you.getLongId());
        List<ILibraryAsset> albums = albumApiClient.getUserAlbums(you.getLongId());
        List<ILibraryAsset> artists = artistApiClient.getUserArtists(you.getLongId());
        assets.addAll(playlists);
        assets.addAll(albums);
        assets.addAll(artists);
        return assets;
        /*Artist artist = new Artist(3L,
                "test artist",
                "test@gmail.com",
                null,
                0,
                false);

        Song doIWantToKnow = new Song(
                "Do I Wanna Know?",
                101L,
                artist,
                272,
                "/music/do-i-wanna-know.mp3",
                null
        );

        Song letItHappen = new Song(
                "Let It Happen",
                102L,
                artist,
                467,
                "/music/let-it-happen.mp3",
                null
        );

        Song ruMine = new Song(
                "R U Mine?",
                103L,
                artist,
                205,
                "/music/r-u-mine.mp3",
                null
        );

        Album am = new Album(
                "AM",
                201,
                artist,
                List.of(doIWantToKnow, ruMine),
                Date.valueOf("2013-09-09")
        );

        Album currents = new Album(
                "Currents",
                202,
                artist,
                List.of(letItHappen),
                Date.valueOf("2015-07-17")
        );

        Playlist chillVibes = new Playlist("Chill Vibes", 301, you);
        chillVibes.addSong(doIWantToKnow);
        chillVibes.addSong(letItHappen);

        Playlist morningHits = new Playlist("Morning Hits", 302, you);
        morningHits.addSong(ruMine);

        return List.of(
                chillVibes,
                morningHits,
                am,
                artist,
                currents,
                doIWantToKnow,
                letItHappen,
                ruMine
        );*/
    }
}
