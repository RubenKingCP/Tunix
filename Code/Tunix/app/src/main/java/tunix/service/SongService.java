package tunix.service;

import tunix.event.EventBus;
import tunix.model.Song;
import tunix.api.SongApiClient;
import tunix.dto.request.SongRequest;

public class SongService {
    private final EventBus eventBus;
    private final SongApiClient songApiClient;

    public SongService(EventBus eventBus, SongApiClient songApiClient) {
        this.eventBus = eventBus;
        this.songApiClient = songApiClient;
    }

    public Song uploadSong(SongRequest songRequest) {
        return songApiClient.uploadSong(songRequest).getData().toSong();
    }

}
