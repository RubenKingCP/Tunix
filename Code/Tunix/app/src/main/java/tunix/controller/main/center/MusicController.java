package tunix.controller.main.center;
import java.util.List;
import javax.swing.JPanel;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.OpenSongViewEvent;
import tunix.navigation.events.SongSelectedEvent;
import tunix.dto.enums.LibraryAssetType;
import tunix.model.ILibraryAsset;
import tunix.model.musicContent.Album;
import tunix.model.musicContent.Playlist;
import tunix.model.musicContent.Song;
import tunix.service.AlbumService;
import tunix.service.PlaylistService;
import tunix.service.auth.SessionService;
import tunix.ui.views.main.LibraryView;
import tunix.ui.views.main.center.MusicView;
import tunix.navigation.events.UpdateLibraryEvent;

public class MusicController {
    private final MusicView musicView;
    private final PlaylistService playlistService;
    private final AlbumService albumService;
    private final EventBus eventBus;

    public MusicController(
            EventBus eventBus,
            LibraryView libraryPanel,
            PlaylistService playlistService,
            AlbumService albumService) {
        this.eventBus = eventBus;
        this.playlistService = playlistService;
        this.musicView = new MusicView(this, libraryPanel);
        this.musicView.setController(this);
        this.albumService = albumService;
    }

    public JPanel getView() {
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
        boolean result = playlistService.addSongToPlaylist(playlistId, song);
        eventBus.publish(new UpdateLibraryEvent());
        return result;
    }

    public boolean removeSongFromPlaylist(int playlistId, Song song) {
        boolean result = playlistService.removeSongFromPlaylist(playlistId, song);
        eventBus.publish(new UpdateLibraryEvent());
        return result;
    }

    public ILibraryAsset fetchFreshAsset(ILibraryAsset asset) {
        if (asset == null) {
            return null;
        }
        if (asset.getType() == tunix.dto.enums.LibraryAssetType.PLAYLIST) {
            Playlist fresh = playlistService.getPlaylistById(asset.getId());
            return fresh != null ? fresh : asset;
        }
        if (asset.getType() == LibraryAssetType.ALBUM) {
            Album fresh = albumService.getAlbumById(asset.getId());
            return fresh != null ? fresh : asset;
        }
        return asset;
    }

    public void onSongClicked(Song song) {
        ILibraryAsset musicViewAsset = musicView.getCurrentMusicAsset();

        List<Song> allSongs = null;
        if (musicViewAsset instanceof Playlist pl) {
            allSongs = pl.getDisplaySongs();
        } else if (musicViewAsset instanceof Album al) {
            allSongs = al.getSongs();
        }

        List<Song> songOrder;
        if (allSongs == null) {
            songOrder = List.of(song);
        } else {
            int index = -1;
            for (int i = 0; i < allSongs.size(); i++) {
                if (allSongs.get(i).getId() == song.getId()) {
                    index = i;
                    break;
                }
            }
            songOrder = index >= 0 ? allSongs.subList(index, allSongs.size()) : List.of(song);
        }

        eventBus.publish(new SongSelectedEvent(songOrder));
    }

    public List<Playlist> getCachedPlaylists() {
        return playlistService.getCachedPlaylists();
    }

    public boolean canDownload() {
        return SessionService.Instance.getUser().isPremium();
    }
}