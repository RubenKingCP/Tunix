package tunixserver.dto.response;

import tunixserver.entities.ArtistEntity;
import tunixserver.entities.SongEntity;

public class SongResponse {
    public int songId;
    public String title;
    public ArtistEntity artist; // nested DTO for artist
    public int duration;
    public String filePathUrl;
    public String coverImageUrl;

    // empty constructor for Jackson
    public SongResponse() {
        
    }

    public static SongResponse fromSong(SongEntity songEntity) {
        return null; // placeholder for mapping logic from SongEntity to SongResponse
    }
}