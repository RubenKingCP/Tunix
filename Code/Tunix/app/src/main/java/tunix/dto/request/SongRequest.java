package tunix.dto.request;
import java.io.File;

import lombok.Getter;

@Getter
public class SongRequest {
    private String title;
    private Long artistId;
    private int duration;
    private String filePathUrl;
    private String coverImageUrl;

    public SongRequest() {}

    public SongRequest(String title, Long artistId, String fileUrlPath, int duration, String coverImage) {
        this.title = title;
        this.artistId = artistId;
        this.filePathUrl = fileUrlPath;
        this.duration = duration;
        this.coverImageUrl = coverImage;
    }
}