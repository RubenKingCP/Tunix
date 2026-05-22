package tunix.service;

import java.sql.Date;
import java.util.List;

import tunix.dto.enums.Role;
import tunix.model.Album;
import tunix.model.Artist;
import tunix.model.ILibraryAsset;
import tunix.model.Playlist;
import tunix.model.Song;
import tunix.model.User;

public class LibraryService {

    public List<ILibraryAsset> getLibraryAssets() {
        User you = new User(1L, "you", "you@example.com", Role.USER);
        you.setDisplayName("You");

        Artist arcticMonkeys = new Artist(
                2L,
                "Arctic Monkeys",
                "arctic@example.com",
                Role.ARTIST,
                "A British rock band known for punchy, melodic songs.",
                1_250_000
        );

        Artist tameImpala = new Artist(
                3L,
                "Tame Impala",
                "tame@example.com",
                Role.ARTIST,
                "A psychedelic rock project focused on atmospheric soundscapes.",
                4_800_000
        );

        Song doIWantToKnow = new Song(
                "Do I Wanna Know?",
                101,
                arcticMonkeys,
                272,
                "/music/do-i-wanna-know.mp3",
                null
        );

        Song letItHappen = new Song(
                "Let It Happen",
                102,
                tameImpala,
                467,
                "/music/let-it-happen.mp3",
                null
        );

        Song ruMine = new Song(
                "R U Mine?",
                103,
                arcticMonkeys,
                205,
                "/music/r-u-mine.mp3",
                null
        );

        Album am = new Album(
                "AM",
                201,
                arcticMonkeys,
                List.of(doIWantToKnow, ruMine),
                Date.valueOf("2013-09-09")
        );

        Album currents = new Album(
                "Currents",
                202,
                tameImpala,
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
                arcticMonkeys,
                tameImpala,
                am,
                currents,
                doIWantToKnow,
                letItHappen,
                ruMine
        );
    }
}
