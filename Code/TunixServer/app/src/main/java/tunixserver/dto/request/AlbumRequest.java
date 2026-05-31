package tunixserver.dto.request;

import java.time.LocalDate;

public class AlbumRequest {

    private String title;
    private Long artistId;
    private LocalDate releaseDate;

    public AlbumRequest() {}

    public AlbumRequest(String title, Long artistId, LocalDate releaseDate) {
        this.title = title;
        this.artistId = artistId;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public Long getArtistId() {
        return artistId;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}