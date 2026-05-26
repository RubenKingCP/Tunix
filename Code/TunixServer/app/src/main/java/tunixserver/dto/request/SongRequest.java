package tunixserver.dto.request;

public class SongRequest {

    private String title;
    private Long artistId;
    private int duration;
    private String filePathUrl;
    private String coverImageUrl;

    public SongRequest() {}

    public SongRequest(String title, Long artistId, int duration,
                        String filePathUrl, String coverImageUrl) {
        this.title = title;
        this.artistId = artistId;
        this.duration = duration;
        this.filePathUrl = filePathUrl;
        this.coverImageUrl = coverImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public Long getArtistId() {
        return artistId;
    }

    public int getDuration() {
        return duration;
    }

    public String getFilePathUrl() {
        return filePathUrl;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setFilePathUrl(String filePathUrl) {
        this.filePathUrl = filePathUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }
}