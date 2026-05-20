package tunixserver.entities;

public class SongEntity {
    private int songId;
    private String title;
    private ArtistEntity artist;
    private int duration;
    private String filePathUrl;
    private String coverImageUrl;

    public SongEntity(String title, int songId, ArtistEntity artist, int duration, String filePathUrl, String coverImageUrl) {
        this.title = title;
        this.songId = songId;
        this.artist = artist;
        this.duration = duration;
        this.filePathUrl = filePathUrl;
        this.coverImageUrl = coverImageUrl;

    }

    public int getId() {
        return songId;
    }
}
