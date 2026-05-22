package tunix.controller.main;

import tunix.event.EventBus;
import tunix.service.MusicPlayerService;
import tunix.view.main.MusicPlayerView;

public class MusicPlayerController {
    private final MusicPlayerService musicPlayerService;
    private final MusicPlayerView musicPlayerView;
    private final EventBus eventBus;

    public MusicPlayerController(MusicPlayerView musicPlayerView, MusicPlayerService musicPlayerService, EventBus eventBus){
        this.musicPlayerService = musicPlayerService;
        this.musicPlayerView = musicPlayerView;
        this.eventBus = eventBus;
    }
}
