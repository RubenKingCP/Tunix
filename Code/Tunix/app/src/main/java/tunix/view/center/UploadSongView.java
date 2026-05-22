package tunix.view.center;

import tunix.controller.UploadSongController;

public class UploadSongView {
    private UploadSongController uploadSongController;

    public UploadSongView() {

    }

    public void display() {
        // Code to display the upload song view
    }

    public void setUploadSongController(UploadSongController uploadSongController) {
        this.uploadSongController = uploadSongController;
    }

    public void onSelectFileClicked() {
        uploadSongController.onSelectFileClicked();
    }

    public void onSubmitButtonClicked() {
        uploadSongController.onSubmitButtonClicked();
    }

    public void displaySelectedSongFile(String filePath) {
        // Code to display the selected file path in the view
    }

    public void displaySelectedImageCover(String imageFilePath) {
        // Code to display the selected image cover in the view
    }

    public void displayError(String message) {
        // Code to display an error message in the view
    }

    public void displaySuccess(String message) {
        // Code to display a success message in the view
    }
    
}
