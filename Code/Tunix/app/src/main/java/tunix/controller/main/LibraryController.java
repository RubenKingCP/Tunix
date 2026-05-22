package tunix.controller.main;

import java.util.List;

import tunix.dto.request.PlaylistCreateRequest;
import tunix.model.ILibraryAsset;
import tunix.service.LibraryService;
import tunix.service.PlaylistService;
import tunix.view.main.LibraryView;

public class LibraryController {
    private final LibraryView libraryView;
    private final LibraryService libraryService;
    private final PlaylistService playlistService;

    public LibraryController(LibraryView libraryView,
                            LibraryService libraryService,
                            PlaylistService playlistService) {

        this.libraryView = libraryView;
        this.libraryService = libraryService;
        this.playlistService = playlistService;
    }

    public void createPlaylist(PlaylistCreateRequest playlistRequest) {
        List<ILibraryAsset> assets = libraryService.getLibraryAssets();
        playlistService.createPlaylist(playlistRequest, assets);
    }
}
