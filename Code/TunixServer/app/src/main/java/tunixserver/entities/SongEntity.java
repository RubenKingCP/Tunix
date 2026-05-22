package tunixserver.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "songs")
public class SongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;

    private String title;

    private int duration;

    private String filePathUrl;

    private String coverImageUrl;

    // IMPORTANT: for now keep this simple (avoid nested entity issues)
    private Long artistId;

    public SongEntity() {
        // JPA requires a no-args constructor
    }

    public SongEntity(String title,
                      Long songId,
                      Long artistId,
                      int duration,
                      String filePathUrl,
                      String coverImageUrl) {
        this.title = title;
        this.songId = songId;
        this.artistId = artistId;
        this.duration = duration;
        this.filePathUrl = filePathUrl;
        this.coverImageUrl = coverImageUrl;
    }

    // getters and setters

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFilePathUrl() {
        return filePathUrl;
    }

    public void setFilePathUrl(String filePathUrl) {
        this.filePathUrl = filePathUrl;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }
}