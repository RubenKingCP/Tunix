package tunix.service;

import java.util.ArrayList;
import java.util.List;
import tunix.model.ILibraryAsset;
import tunix.model.account.Account;
import tunix.service.auth.SessionService;
import tunix.api.*;
import tunix.dto.response.AlbumResponse;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.LibraryResponse;
import tunix.dto.response.PlaylistResponse;
import tunix.dto.response.SongResponse;

public class LibraryService {
        private final LibraryApiClient libraryApiClient;

        public LibraryService(LibraryApiClient apiClient) {
                this.libraryApiClient = apiClient;
        }
        
    public List<ILibraryAsset> getLibraryAssets() {
        Account you = SessionService.Instance.getAccount();
        if (you == null) {
            return new ArrayList<>();
        }
        List<ILibraryAsset> assets = new ArrayList<>();

        ApiResponse<LibraryResponse> response =
                libraryApiClient.getLibrary(you.getLongId());

        if (response == null || !response.isSuccess() || response.getData() == null) {
            return assets;
        }

        // PLAYLISTS
        List<ILibraryAsset> playlists = response.getData().getPlaylists()
                .stream()
                .map(PlaylistResponse::toPlaylist)
                .map(p -> (ILibraryAsset) p)
                .toList();

        System.out.println("PLAYLISTS mapped: " + playlists.size());
        playlists.forEach(p -> System.out.println(" -> " + p.getTitle()));


        // ALBUMS
        List<ILibraryAsset> albums = response.getData().getAlbums()
                .stream()
                .map(AlbumResponse::toAlbum)
                .map(a -> (ILibraryAsset) a)
                .toList();

        System.out.println("ALBUMS mapped: " + albums.size());
        albums.forEach(a -> System.out.println(" -> " + a.getTitle()));


        // SONGS
        List<ILibraryAsset> songs = response.getData().getSongs()
                .stream()
                .map(SongResponse::toSong)
                .map(s -> (ILibraryAsset) s)
                .toList();

        System.out.println("SONGS mapped: " + songs.size());
        songs.forEach(s -> System.out.println(" -> " + s.getTitle()));


        // FINAL ASSETS
        assets.addAll(playlists);
        assets.addAll(albums);
        assets.addAll(songs);

        System.out.println("TOTAL assets before return: " + assets.size());
        assets.forEach(a ->
                System.out.println("ASSET -> type=" + a.getType() + ", title=" + a.getTitle())
        );

        return assets;
        // /*Artist artist = new Artist(3L,
//                 "test artist",
//                 "test@gmail.com",
//                 null,
//                 0,
//                 false);

//         Song doIWantToKnow = new Song(
//                 "Do I Wanna Know?",
//                 101L,
//                 artist,
//                 272,
//                 "/music/do-i-wanna-know.mp3",
//                 null
//         );

//         Song letItHappen = new Song(
//                 "Let It Happen",
//                 102L,
//                 artist,
//                 467,
//                 "/music/let-it-happen.mp3",
//                 null
//         );

//         Song ruMine = new Song(
//                 "R U Mine?",
//                 103L,
//                 artist,
//                 205,
//                 "/music/r-u-mine.mp3",
//                 null
//         );

//         Album am = new Album(
//                 "AM",
//                 201,
//                 artist,
//                 List.of(doIWantToKnow, ruMine),
//                 Date.valueOf("2013-09-09")
//         );

//         Album currents = new Album(
//                 "Currents",
//                 202,
//                 artist,
//                 List.of(letItHappen),
//                 Date.valueOf("2015-07-17")
//         );

//         Playlist chillVibes = new Playlist("Chill Vibes", 301, you);
//         chillVibes.addSong(doIWantToKnow);
//         chillVibes.addSong(letItHappen);

//         Playlist morningHits = new Playlist("Morning Hits", 302, you);
//         morningHits.addSong(ruMine);

//         return List.of(
//                 chillVibes,
//                 morningHits,
//                 am,
//                 artist,
//                 currents,
//                 doIWantToKnow,
//                 letItHappen,
//                 ruMine
//         );*/
    }
}
