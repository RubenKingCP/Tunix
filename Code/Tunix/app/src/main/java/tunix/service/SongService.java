package tunix.service;

import tunix.navigation.events.EventBus;
import tunix.api.SongApiClient;
import tunix.dto.request.SongRequest;
import tunix.model.musicContent.Song;

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
