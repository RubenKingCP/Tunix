package tunix.controller;

import tunix.service.SongService;
import tunix.ui.views.main.center.UploadSongView;
import tunix.dto.request.SongRequest;
import tunix.model.Song;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.SongUploadedEvent;

public class UploadSongController {
    private final UploadSongView uploadSongView;
    private final SongService songService;
    private final EventBus eventBus;

    public UploadSongController(UploadSongView uploadSongView, SongService songService, EventBus eventBus) {
        this.uploadSongView = uploadSongView;
        this.songService = songService;
        this.eventBus = eventBus;
    }

    public void onSelectFileClicked() {
        // Logic to open file chooser and get selected file path
        
        // Logic to upload file anc create song entry in the database

    }

    public void onSelectImageCoverClicked() {
        // Logic to open file chooser and get selected image file path
    }

    public void draw() {
        uploadSongView.display();
    }

    public void displaySelectedFile(String filePath) {
        uploadSongView.displaySelectedSongFile(filePath);
    }

    public void displaySelectedImageCover(String imageFilePath) {
        uploadSongView.displaySelectedImageCover(imageFilePath);
    }

    public void onSubmitButtonClicked() {
        SongRequest songRequest = new SongRequest(null, 0, null, 0, null);

        songService.uploadSong(songRequest);
    }

    public void displayError(String message) {
        uploadSongView.displayError(message);
    }

    public void displaySuccess(String message) {
        uploadSongView.displaySuccess(message);
    }

    public void songUploadSuccess() {
        eventBus.publish(new SongUploadedEvent());
        // Logic to handle successful song upload, such as navigating back to the artist profile view or displaying a success message
    }
}
