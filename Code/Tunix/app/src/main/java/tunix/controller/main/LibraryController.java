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
import tunix.navigation.events.UpdateLibraryEvent;
import tunix.navigation.events.OpenAlbumViewEvent;
import tunix.model.musicContent.Album;
import tunix.navigation.events.OpenSongViewEvent;

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
        this.appContext.eventBus.subscribe(UpdateLibraryEvent.class, e -> onLibraryAssetsUpdated());
    }

    public LibraryView getView() {
        return libraryView;
    }

    public void onLibraryAssetsUpdated() {
        List<ILibraryAsset> updatedAssets = libraryService.getLibraryAssets();
        libraryView.setLibraryAssets(updatedAssets);
        ;
    }

    public boolean createPlaylist(PlaylistCreateRequest playlistRequest) {
        System.out.println("LibraryControler: PlaylistCreationButtonClicked"  );
        List<ILibraryAsset> assets = libraryService.getLibraryAssets();
        boolean ok = playlistService.createPlaylist(playlistRequest, assets);

        if (ok) {
            // reload library assets from backend and update the view so the new playlist appears immediately
            List<ILibraryAsset> updated = libraryService.getLibraryAssets();
            libraryView.setLibraryAssets(updated);
        } else {
            System.err.println("Failed to create playlist");
        }
        return ok;
    }

    public void playlistClicked(ILibraryAsset asset) {
        appContext.eventBus.publish(new LibraryPlaylistClicked(asset));
    }

    public void albumClicked(Album album) {
        System.out.println("ALBUM CLICKED AND SEND AN EVENT");
        System.out.println("A;BUM SEND" + album.getTitle());
        appContext.eventBus.publish(new OpenAlbumViewEvent(album));
    }
    public void songClicked(Song song) {
        appContext.eventBus.publish(new OpenSongViewEvent(song));
    }

    
    public void artistClicked(Artist artist) {
    appContext.eventBus.publish(
            new OpenArtistViewEvent(artist)
    );
}
   private MusicController musicController; // ?

  

   public List<ILibraryAsset> getLibraryAssets() {
        return libraryService.getLibraryAssets();
   }
}
