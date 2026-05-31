package tunix.service;

import tunix.navigation.events.EventBus;

import java.util.List;

import tunix.api.SongApiClient;
import tunix.dto.request.SongRequest;
import tunix.dto.response.ApiResponse;
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
        return new Song(songResponse.getTitle(), songResponse.getId(), new Artist(songResponse.getArtistId(), songResponse.getArtistName(), null, null, 0, false), songResponse.getDuration(), songResponse.getFilePathUrl(), null);
    }
   
    public List<Song> getSongs() {
        try {
            System.out.println("SongService: Sending request to api client to fetch songs by name");
            ApiResponse<List<SongResponse>> response = songApiClient.getSongs();
            System.out.println("SongService: Got responses");
                return response.getData()
                    .stream()
                    .map(this::toModel)
                    .toList();
        } catch(Exception exception) {
            System.out.println("Caught exception " + exception);
            return getDummySongs();
        }
    }
   
    //Dummy data so that i don't kurt cobain myself
    public List<Song> getDummySongs() {
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

    public Song toModel(SongResponse response) {

        Artist artist = new Artist(
                Long.valueOf(response.getArtistId()),
                response.getArtistName(),
                null,
                null,
                0,
                false
        );

        return new Song(
                response.getTitle(),
                response.getId(),
                artist,
                response.getDuration(),
                response.getFilePathUrl(),
                response.getCoverImageUrl()
        );
    }
    public Song getSongById(int songId) {
        try {
            System.out.println("SongService: Fetching song with id: " + songId);
            ApiResponse<SongResponse> response = songApiClient.getSongById(songId);
            if (response == null || !response.isSuccess() || response.getData() == null) {
                throw new RuntimeException("Failed to fetch song with id: " + songId);
            }
            return toModel(response.getData());
        } catch(Exception exception) {
            System.out.println("Caught exception " + exception);
            return null;
        }
    }
}

