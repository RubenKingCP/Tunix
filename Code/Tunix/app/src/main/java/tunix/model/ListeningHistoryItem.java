package tunix.model;

import java.time.LocalDateTime;

public class ListeningHistoryItem {

    private Song song;
    private LocalDateTime playedAt;

    public ListeningHistoryItem(Song song, LocalDateTime playedAt) {
        this.song = song;
        this.playedAt = playedAt;
    }

    public Song getSong() {
        return song;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }
}
