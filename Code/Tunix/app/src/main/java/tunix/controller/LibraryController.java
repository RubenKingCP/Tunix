package tunix.controller;

import java.util.List;

import tunix.dto.request.PlaylistCreateRequest;
import tunix.model.ILibraryAsset;
import tunix.service.LibraryService;
import tunix.service.PlaylistService;
import tunix.view.component.LibraryView;

public class LibraryController {
    private final LibraryView libraryView;
    private final LibraryService libraryService;
    private final PlaylistService playlistService;
    private List<ILibraryAsset> libraryAssets;

    public LibraryController(LibraryView libraryView, LibraryService libraryService, PlaylistService playlistService, List<ILibraryAsset> libraryAssets) {
        this.libraryService = libraryService;
        this.libraryView = libraryView;
        this.playlistService = playlistService;
        this.libraryAssets = libraryAssets;
    }

    public void createPlaylist(PlaylistCreateRequest playlistRequest) {
        playlistService.createPlaylist(playlistRequest, libraryAssets);
    }
}
