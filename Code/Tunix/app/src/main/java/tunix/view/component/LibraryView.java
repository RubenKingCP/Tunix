package tunix.view.component;

import java.util.List;

import tunix.controller.LibraryController;
import tunix.dto.request.PlaylistCreateRequest;
import tunix.view.library.LibraryAssetView;

public class LibraryView {
    
    private final LibraryController libraryController;
    private List<LibraryAssetView> libraryAssetViews;

    public LibraryView (LibraryController libraryController) {
        this.libraryController = libraryController;
    }

    public void onRightClickOnLibrary() {
        this.showLibraryOptions();
    }

    public void showLibraryOptions() {

    }

    public void onCreatePlaylistClicked() {
        this.onCreatePlaylistClicked();
    }

    public void showPlaylistCreationMenu() {

    }

    public void onPlaylistCreateConfirmClicked(PlaylistCreateRequest playlistRequest) {
        libraryController.createPlaylist(playlistRequest);
    }

}
