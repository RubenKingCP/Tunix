package tunix.navigation.events;

public class SongSelectedEvent {
    private final java.util.List<tunix.model.musicContent.Song> songOrder;
    public SongSelectedEvent(java.util.List<tunix.model.musicContent.Song> songOrder) {
        this.songOrder = songOrder;
    }
    public java.util.List<tunix.model.musicContent.Song> getSongOrder() {
        return songOrder;
    }
}
