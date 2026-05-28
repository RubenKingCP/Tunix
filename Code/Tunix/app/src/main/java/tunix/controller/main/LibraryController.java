package tunix.controller.main;

import java.util.List;

import javax.swing.JPanel;

import tunix.controller.main.center.MusicController;
import tunix.dto.request.PlaylistCreateRequest;
import tunix.model.AppContext;
import tunix.model.ILibraryAsset;
import tunix.navigation.events.LibraryPlaylistClicked;
import tunix.service.LibraryService;
import tunix.service.PlaylistService;
import tunix.ui.views.main.LibraryView;
import tunix.model.account.Artist;
import tunix.model.musicContent.Song;
import tunix.navigation.events.OpenArtistViewEvent;
import tunix.navigation.events.OpenAlbumViewEvent;
import tunix.model.musicContent.Album;


public class LibraryController {
    private final LibraryService libraryService;
    private final PlaylistService playlistService;
    private final AppContext appContext;
    private final LibraryView libraryView;

    public LibraryController(LibraryService libraryService,
                            PlaylistService playlistService,
                            AppContext context) {

        this.libraryService = libraryService;
        this.playlistService = playlistService;
        this.appContext = context;
        this.libraryView = new LibraryView();
        this.libraryView.setController(this);
        this.libraryView.setLibraryAssets(libraryService.getLibraryAssets());
    }

    public LibraryView getView() {
        return libraryView;
    }

    public void createPlaylist(PlaylistCreateRequest playlistRequest) {
        System.out.println("LibraryControler: PlaylistCreationButtonClicked"  );
        List<ILibraryAsset> assets = libraryService.getLibraryAssets();
        playlistService.createPlaylist(playlistRequest, assets);
    }

    public void playlistClicked(ILibraryAsset asset) {
        appContext.eventBus.publish(new LibraryPlaylistClicked(asset));
    }

    public void albumClicked(Album album) {
        appContext.eventBus.publish(new OpenAlbumViewEvent(album));
    }
    public void songClicked(Song song) {
        appContext.eventBus.publish(new OpenSongViewEvent(song));
    }
    public final class OpenSongViewEvent {

        private final Song song;

        public OpenSongViewEvent(Song song) {
            this.song = song;
        }

        public Song getSong() {
            return song;
        }
    }
    
    public void artistClicked(Artist artist) {
    appContext.eventBus.publish(
            new OpenArtistViewEvent(artist)
    );
}
   private MusicController musicController;

  

   public List<ILibraryAsset> getLibraryAssets() {
        return libraryService.getLibraryAssets();
   }
}
