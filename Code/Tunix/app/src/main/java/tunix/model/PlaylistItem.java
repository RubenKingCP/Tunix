package tunix.model;

public class PlaylistItem {
    private Song song;
    private int position;

    public PlaylistItem(Song song, int position) {
        this.song = song;
        this.position = position;
    }

    public Song getSong() {
        return song;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}