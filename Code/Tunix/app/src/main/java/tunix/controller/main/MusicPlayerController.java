package tunix.controller.main;

import javax.swing.JPanel;

import tunix.navigation.events.EventBus;
import tunix.service.MusicPlayerService;
import tunix.ui.views.main.MusicPlayerView;

public class MusicPlayerController {
    private final MusicPlayerService musicPlayerService;
    private final MusicPlayerView musicPlayerView;
    private final EventBus eventBus;

    public MusicPlayerController(MusicPlayerService musicPlayerService, EventBus eventBus){
        this.musicPlayerService = musicPlayerService;
        this.eventBus = eventBus;
        this.musicPlayerView = new MusicPlayerView();
        this.musicPlayerView.setController(this);
    }

    public JPanel getView() {
        return musicPlayerView;
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
