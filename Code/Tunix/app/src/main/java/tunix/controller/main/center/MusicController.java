package tunix.controller.main.center;

import java.util.List;
import javax.swing.JPanel;
import tunix.navigation.events.EventBus;
import tunix.model.ILibraryAsset;
import tunix.model.musicContent.Playlist;
import tunix.model.musicContent.Song;
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
        this.musicView = new MusicView(this, libraryPanel);
        this.musicView.setController(this);
    }

    public JPanel getView() { //You sure?
        return musicView;
    }

    public void drawView() {
        ensurePlaylistsLoaded();
        musicView.initGui();
    }

    public void ensurePlaylistsLoaded() {
        playlistService.loadUserPlaylists();
    }

    public boolean addSongToPlaylist(int playlistId, Song song) {
        return playlistService.addSongToPlaylist(playlistId, song);
    }

    public boolean removeSongFromPlaylist(int playlistId, Song song) {
        return playlistService.removeSongFromPlaylist(playlistId, song);
    }

    public ILibraryAsset fetchFreshAsset(ILibraryAsset asset) {
        if (asset == null) {
            return null;
        }
        if (asset.getType() == tunix.dto.enums.LibraryAssetType.PLAYLIST) {
            Playlist fresh = playlistService.getPlaylistById(asset.getId());
            return fresh != null ? fresh : asset;
        }
        return asset;
    }

    public List<Playlist> getCachedPlaylists() {
        return playlistService.getCachedPlaylists();
    }
}