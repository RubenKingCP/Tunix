package tunix.model;

public class Song implements ILibraryAsset {
    private final String title;
    private final int songId;
    private final Artist artist;
    private final int duration; // Duration in seconds
    private final String filePathUrl;
    private final String coverImageUrl; // Optional cover image URL

    public Song(String title, int songId, Artist artist, int duration, String filePathUrl, String coverImageUrl) {
        this.title = title;
        this.songId = songId;
        this.artist = artist;
        this.duration = duration;
        this.filePathUrl = filePathUrl;
        this.coverImageUrl = coverImageUrl;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getId() {
        return songId;
    }

    @Override
    public String getType() {
        return "Song";
    }

    @Override
    public String getSubtitle() {
        return artist.getTitle();
    }

    public Artist getArtist() {
        return artist;
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
}
