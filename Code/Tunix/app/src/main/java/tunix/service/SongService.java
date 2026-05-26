package tunix.service;

import tunix.navigation.events.EventBus;

import java.util.List;

import tunix.api.SongApiClient;
import tunix.dto.request.SongRequest;
import tunix.dto.response.SongResponse;
import tunix.model.account.Artist;
import tunix.model.musicContent.Song;

public class SongService {
    private final EventBus eventBus;
    private final SongApiClient songApiClient;

    public SongService(EventBus eventBus, SongApiClient songApiClient) {
        this.eventBus = eventBus;
        this.songApiClient = songApiClient;
    }

    public Song uploadSong(SongRequest songRequest) {
        System.out.println("SongService: Sending request to server to upload song");
        SongResponse songResponse = songApiClient.uploadSong(songRequest).getData();
        return new Song(songResponse.getTitle(), songResponse.getSongId(), songResponse.getArtist(), 0, null, null);
    }
    //Dummy data so that i don't kurt cobain myself
   public List<Song> getSongs() {
    Artist weeknd   = new Artist(1L, "The Weeknd",    "weeknd@email.com",   "Canadian singer-songwriter.", 50000, true);
    Artist duaLipa  = new Artist(2L, "Dua Lipa",      "dua@email.com",      "British-Albanian pop star.", 45000, true);
    Artist kidLaroi = new Artist(3L, "Kid LAROI",     "laroi@email.com",    "Australian rapper.",          30000, true);
    Artist billie   = new Artist(4L, "Billie Eilish", "billie@email.com",   "American singer-songwriter.", 60000, true);
    Artist harry    = new Artist(5L, "Harry Styles",  "harry@email.com",    "British pop artist.",         55000, true);

    return List.of(
        new Song("Blinding Lights",  1L, weeknd,   200, null, null),
        new Song("Levitating",       2L, duaLipa,  203, null, null),
        new Song("Stay",             3L, kidLaroi, 141, null, null),
        new Song("Bad Guy",          4L, billie,   194, null, null),
        new Song("Watermelon Sugar", 5L, harry,    174, null, null)
    );
}
}

