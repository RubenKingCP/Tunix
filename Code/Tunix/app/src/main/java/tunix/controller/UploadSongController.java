package tunix.controller;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import tunix.dto.request.SongRequest;
import tunix.model.account.Account;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.SongUploadedEvent;
import tunix.service.SongService;
import tunix.service.auth.SessionService;
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

        fileChooser.setFileFilter(
            new javax.swing.filechooser.FileNameExtensionFilter(
                "Audio Files (*.mp3, *.wav, *.flac, *.aac, *.ogg)",
                "mp3", "wav", "flac", "aac", "ogg"
            )
        );

        fileChooser.setAcceptAllFileFilterUsed(false);

        int result = fileChooser.showOpenDialog(uploadSongView);

        if (result == JFileChooser.APPROVE_OPTION) {

            selectedSongFile = fileChooser.getSelectedFile();

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

        String fileName = selectedSongFile.getName().toLowerCase();
        boolean isAudio = fileName.endsWith(".mp3")
                || fileName.endsWith(".wav")
                || fileName.endsWith(".flac")
                || fileName.endsWith(".aac")
                || fileName.endsWith(".ogg");

        if (!isAudio) {

            uploadSongView.displayError(
                    "Invalid file type. Please select an audio file (mp3, wav, flac, aac, ogg)."
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
        long artist_id;
        try {
            artist_id = SessionService.Instance.getArtist().getId();
        } catch (Exception e) {
            artist_id = 3L;
        }

        SongRequest songRequest =
                new SongRequest(
                        title,
                        artist_id,
                        selectedSongFile.getAbsolutePath(),
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
            System.out.println("Upload failed");
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