package tunix.controller.main.center;

import javax.swing.JPanel;

import tunix.navigation.events.EventBus;
import tunix.ui.views.main.LibraryView;
import tunix.ui.views.main.center.MusicView;

public class MusicController {
    private final MusicView musicView;

    public MusicController(EventBus eventBus, LibraryView libraryPanel) {
        this.musicView = new MusicView(this, libraryPanel);
        this.musicView.setController(this);
    }

    public JPanel getView() {
        return musicView;
    }

    public void drawView() {
        musicView.initGui();
    }
}
