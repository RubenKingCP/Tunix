package tunix.controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import tunix.dto.request.SongRequest;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.SongUploadedEvent;
import tunix.service.SongService;
import tunix.ui.views.main.center.UploadSongView;

public class UploadSongController {

    private final UploadSongView uploadSongView;

    private final SongService songService;

    private final EventBus eventBus;

    private File selectedSongFile;

    public UploadSongController(
            UploadSongView uploadSongView,
            SongService songService,
            EventBus eventBus
    ) {

        this.uploadSongView = uploadSongView;

        this.songService = songService;

        this.eventBus = eventBus;

        this.uploadSongView.setController(this);
    }

    //-------------------------------------------------
    // FILE PICKER
    //-------------------------------------------------

    public void onSelectFileClicked() {

        JFileChooser fileChooser = new JFileChooser();

        int result =
                fileChooser.showOpenDialog(uploadSongView);

        if (result == JFileChooser.APPROVE_OPTION) {

            selectedSongFile =
                    fileChooser.getSelectedFile();

            uploadSongView.displaySelectedSongFile(
                    selectedSongFile.getAbsolutePath()
            );
        }
    }

    //-------------------------------------------------
    // UNUSED
    //-------------------------------------------------

    public void onSelectImageCoverClicked() {

        // intentionally unused
        // use case does not include image covers
    }

    //-------------------------------------------------
    // VIEW
    //-------------------------------------------------

    public void draw() {

        uploadSongView.display();
    }

    public void displaySelectedFile(String filePath) {

        uploadSongView.displaySelectedSongFile(
                filePath
        );
    }

    public void displaySelectedImageCover(
            String imageFilePath
    ) {

        uploadSongView.displaySelectedImageCover(
                imageFilePath
        );
    }

    //-------------------------------------------------
    // SUBMIT
    //-------------------------------------------------

    public void onSubmitButtonClicked() {

        String title =
                uploadSongView.getSongTitle();

        int trackNumber =
                uploadSongView.getTrackNumber();

        //-------------------------------------------------
        // VALIDATION
        //-------------------------------------------------

        if (selectedSongFile == null) {

            uploadSongView.displayError(
                    "Please select a song file."
            );

            return;
        }

        if (title == null || title.isBlank()) {

            uploadSongView.displayError(
                    "Please enter a song title."
            );

            return;
        }

        if (trackNumber <= 0) {

            uploadSongView.displayError(
                    "Track number must be greater than 0."
            );

            return;
        }

        //-------------------------------------------------
        // REQUEST
        //-------------------------------------------------

        SongRequest songRequest =
                new SongRequest(
                        title,
                        1, // temporary artist id
                        selectedSongFile,
                        0, // duration placeholder
                        null
                );

        //-------------------------------------------------
        // UPLOAD
        //-------------------------------------------------

        try {

            songService.uploadSong(songRequest);

            uploadSongView.displaySuccess(
                    "Song uploaded successfully."
            );

            songUploadSuccess();

        } catch (Exception exception) {

            uploadSongView.displayError(
                    "Upload failed: "
                            + exception.getMessage()
            );
        }
    }

    //-------------------------------------------------
    // HELPERS
    //-------------------------------------------------

    public void displayError(String message) {

        uploadSongView.displayError(message);
    }

    public void displaySuccess(String message) {

        uploadSongView.displaySuccess(message);
    }

    public void songUploadSuccess() {

        eventBus.publish(
                new SongUploadedEvent()
        );
    }
    public JPanel getView() {
    return uploadSongView;
    }
}