package tunix.controller.main.center;

import java.util.List;

import javax.swing.JPanel;

import tunix.navigation.events.EventBus;
import tunix.model.musicContent.Playlist;
import tunix.service.PlaylistService;
import tunix.ui.views.main.LibraryView;
import tunix.ui.views.main.center.MusicView;

public class MusicController {

    private final MusicView musicView;
    private final PlaylistService playlistService;

    public MusicController(
            EventBus eventBus,
            LibraryView libraryPanel,
            PlaylistService playlistService) {

        this.playlistService = playlistService;

        this.musicView =
                new MusicView(this, libraryPanel);

        this.musicView.setController(this);
    }

    public JPanel getView() {
        return musicView;
    }

    public void drawView() {
        musicView.initGui();
    }

    public boolean addSongToPlaylist(int playlistId, int songId) {

        return playlistService.addSongToPlaylist(
                playlistId,
                songId);
    }

    public List<Playlist> getCachedPlaylists() {
        return playlistService.getCachedPlaylists();
    }
}