package tunixserver.dto.request;

import java.time.LocalDateTime;

public class AlbumRequest {
    public String title;
    public Long artistId;
    public LocalDateTime releaseDate;

    public AlbumRequest() {
    }

    public AlbumRequest(String title, Long artistId, LocalDateTime releaseDate) {
        this.title = title;
        this.artistId = artistId;
        this.releaseDate = releaseDate;
    }
}
