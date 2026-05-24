package tunix.ui.views.profile;

import tunix.controller.ArtistProfileController;

public class ArtistProfileView {
    private ArtistProfileController artistProfileController;

    public ArtistProfileView() {

    }

    public void setArtistProfileController(ArtistProfileController artistProfileController) {
        this.artistProfileController = artistProfileController;
    }

    public void onuUploadSongClicked() {
        artistProfileController.onUploadSongClicked();
    }

    public void display() {
        // Code to display the artist profile view
    }
}
