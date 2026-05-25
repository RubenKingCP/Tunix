package tunix.controller.main;

import java.util.List;

import tunix.dto.request.PlaylistCreateRequest;
import tunix.model.AppContext;
import tunix.model.ILibraryAsset;
import tunix.navigation.events.LibraryPlaylistClicked;
import tunix.service.LibraryService;
import tunix.service.PlaylistService;
import tunix.ui.views.main.LibraryView;

public class LibraryController {
    private final LibraryService libraryService;
    private final PlaylistService playlistService;
    private final AppContext appContext;

    public LibraryController(LibraryView libraryView,
                            LibraryService libraryService,
                            PlaylistService playlistService,
                            AppContext context) {

        this.libraryService = libraryService;
        this.playlistService = playlistService;
        this.appContext = context;

        libraryView.setLibraryAssets(libraryService.getLibraryAssets());
    }

    public void createPlaylist(PlaylistCreateRequest playlistRequest) {
        List<ILibraryAsset> assets = libraryService.getLibraryAssets();
        playlistService.createPlaylist(playlistRequest, assets);
    }

    public void playlistClicked(ILibraryAsset asset) {
        appContext.eventBus.publish(new LibraryPlaylistClicked(asset));
    }

    public void albumClicked() {

    }

    public void songClicked() {

    }
}
