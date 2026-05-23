package tunix.dto.request;
import java.io.File;

public class SongRequest {
    private String title;
    private int artistId;
    private File mp3file;
    private int duration; // Duration in seconds
    private File coverImage; // Optional cover image file

    public SongRequest(String title, int artistId, File mp3file, int duration, File coverImage) {
        this.title = title;
        this.artistId = artistId;
        this.mp3file = mp3file;
        this.duration = duration;
        this.coverImage = coverImage;
    }
}
 