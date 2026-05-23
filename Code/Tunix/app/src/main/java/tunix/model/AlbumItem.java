package tunix.model;

public class AlbumItem {
    private Song song;
    private int trackNumber;


    public AlbumItem(Song song, int trackNumber) {
        this.song = song;
        this.trackNumber = trackNumber;
    }

    public Song getSong() {
        return song;
    }

    public int getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(int trackNumber) {
        this.trackNumber = trackNumber;
    }
}
