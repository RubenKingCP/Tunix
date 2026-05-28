package tunix.controller.main.center;

import javax.swing.JPanel;

import tunix.navigation.events.EventBus;
import tunix.service.PlaylistService;
import tunix.ui.views.main.LibraryView;
import tunix.ui.views.main.center.MusicView;

public class MusicController {
    private final MusicView musicView;
    private final PlaylistService playlistService;

    public MusicController(EventBus eventBus, LibraryView libraryPanel, PlaylistService playlistService) {
        this.musicView = new MusicView(this, libraryPanel);
        this.musicView.setController(this);
        this.playlistService = playlistService;
    }

    public JPanel getView() {
        return musicView;
    }

    public void drawView() {
        musicView.initGui();
    }

    public void addSongToPlaylist(int id, int id2) {
        playlistService.addSongToPlaylist(id, id2);
    }
}
