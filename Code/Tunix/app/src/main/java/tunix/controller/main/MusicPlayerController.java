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

    public void onNextButtonClicked() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onNextButtonClicked'");
    }

    public void onPlayPauseButtonClicked() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onPlayPauseButtonClicked'");
    }

    public void onPreviousButtonClicked() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onPreviousButtonClicked'");
    }

    public void onShuffleButtonClicked() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onShuffleButtonClicked'");
    }
}
