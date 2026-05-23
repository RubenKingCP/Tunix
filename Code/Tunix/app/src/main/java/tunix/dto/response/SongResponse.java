package tunix.dto.response;

import tunix.model.Artist;
import tunix.model.Song;

public class SongResponse {
    public int songId;
    public String title;
    public Artist artist; // nested DTO for artist
    public int duration;
    public String filePathUrl;
    public String coverImageUrl;

    // empty constructor for Jackson
    public SongResponse() {}

    public Song toSong() {
        return new Song(title, songId, artist, duration, filePathUrl, coverImageUrl);
    }
}