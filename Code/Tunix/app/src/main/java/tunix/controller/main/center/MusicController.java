package tunix.controller.main.center;

import tunix.navigation.events.EventBus;
import tunix.ui.views.main.center.MusicView;

public class MusicController {
    private final MusicView musicView;
    public MusicController(MusicView musicView, EventBus eventBus) {
        this.musicView = musicView;
    }

    public void drawView() {
        musicView.initGui();
    }
}
