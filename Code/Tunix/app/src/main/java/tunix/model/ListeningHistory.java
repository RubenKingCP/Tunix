package tunix.model;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class ListeningHistory {

    private List<ListeningHistoryItem> history = new ArrayList<>();

    public void add(Song song) {
        history.add(new ListeningHistoryItem(song, LocalDateTime.now()));
    }

    public List<ListeningHistoryItem> getHistory() {
        return history;
    }
}