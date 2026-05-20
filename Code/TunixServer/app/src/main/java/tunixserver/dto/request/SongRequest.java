package tunixserver.dto.request;

public class SongRequest {
    public String title;
    public int artistId;
    public int duration;
    public String filePathIrl;
    public String coverImageUrl;

    public SongRequest() {
        
    }

    public String getTitle() {
        return title;
    }

    public int getArtistId() {
        return artistId;
    }
}
