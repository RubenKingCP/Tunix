package tunix.view.main;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import tunix.controller.main.LibraryController;
import tunix.dto.request.PlaylistCreateRequest;
import tunix.view.library.LibraryAssetView;

public class LibraryView extends JPanel{
    public LibraryView(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    private LibraryController libraryController;
    private List<LibraryAssetView> libraryAssetViews;


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

    public void setController(LibraryController libraryController) {
        this.libraryController = libraryController;
    }
}
