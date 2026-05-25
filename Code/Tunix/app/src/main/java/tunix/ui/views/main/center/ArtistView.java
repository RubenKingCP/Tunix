package tunix.ui.views.main.center;

import tunix.controller.ArtistProfileController;
import tunix.controller.UploadSongController;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.SwitchCenterScreenEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ArtistView extends JPanel {

    private static final Color BACKGROUND_COLOR = new Color(18, 18, 18);
    private static final Color HEADER_COLOR = new Color(25, 25, 25);
    private static final Color BUTTON_COLOR = new Color(30, 215, 96);
    private static final Color TEXT_COLOR = Color.WHITE;

    private final EventBus eventBus;

    private UploadSongController uploadController;

    private JLabel selectedSongLabel;
    private JLabel selectedCoverLabel;

    private JTextField titleField;
    private JTextField albumField;

    private JSpinner trackNumberSpinner;

    public ArtistView(EventBus eventBus) {

        this.eventBus = eventBus;

        initializeUI();
    }

    public void setUploadController(UploadSongController uploadController) {

        this.uploadController = uploadController;
    }

    private void initializeUI() {

        setLayout(new BorderLayout());

        setBackground(BACKGROUND_COLOR);

        add(createHeader(), BorderLayout.NORTH);
        add(createMainContent(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {

        JPanel headerPanel = new JPanel(new BorderLayout());

        headerPanel.setBackground(HEADER_COLOR);

        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        JLabel title = new JLabel("Artist Upload Center");

        title.setForeground(TEXT_COLOR);

        title.setFont(new Font("Arial", Font.BOLD, 30));

        headerPanel.add(title, BorderLayout.WEST);

        return headerPanel;
    }

    private JPanel createMainContent() {

        JPanel contentPanel = new JPanel(new GridBagLayout());

        contentPanel.setBackground(BACKGROUND_COLOR);

        contentPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(12, 12, 12, 12);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.anchor = GridBagConstraints.WEST;

        //-------------------------------------------------
        // SONG FILE SECTION
        //-------------------------------------------------

        JLabel songFileTitle = createLabel("Song File:");

        selectedSongLabel = createLabel("No file selected");

        JButton selectSongButton = createButton("Choose Song");

        selectSongButton.addActionListener(e -> {

            if (uploadController != null) {

                uploadController.onSelectFileClicked();
            }
        });

        //-------------------------------------------------
        // COVER IMAGE SECTION
        //-------------------------------------------------

        JLabel coverTitle = createLabel("Album Cover:");

        selectedCoverLabel = createLabel("No image selected");

        JButton selectCoverButton = createButton("Choose Cover");

        selectCoverButton.addActionListener(e -> {

            if (uploadController != null) {

                uploadController.onSelectImageCoverClicked();
            }
        });

        //-------------------------------------------------
        // SONG TITLE
        //-------------------------------------------------

        JLabel titleLabel = createLabel("Song Title:");

        titleField = new JTextField(25);

        //-------------------------------------------------
        // ALBUM NAME
        //-------------------------------------------------

        JLabel albumLabel = createLabel("Album:");

        albumField = new JTextField(25);

        //-------------------------------------------------
        // TRACK NUMBER
        //-------------------------------------------------

        JLabel trackLabel = createLabel("Track Number:");

        trackNumberSpinner = new JSpinner(
                new SpinnerNumberModel(1, 1, 100, 1)
        );

        //-------------------------------------------------
        // ROW 1
        //-------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 0;

        contentPanel.add(songFileTitle, gbc);

        gbc.gridx = 1;

        contentPanel.add(selectedSongLabel, gbc);

        gbc.gridx = 2;

        contentPanel.add(selectSongButton, gbc);

        //-------------------------------------------------
        // ROW 2
        //-------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 1;

        contentPanel.add(coverTitle, gbc);

        gbc.gridx = 1;

        contentPanel.add(selectedCoverLabel, gbc);

        gbc.gridx = 2;

        contentPanel.add(selectCoverButton, gbc);

        //-------------------------------------------------
        // ROW 3
        //-------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 2;

        contentPanel.add(titleLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;

        contentPanel.add(titleField, gbc);

        //-------------------------------------------------
        // ROW 4
        //-------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;

        contentPanel.add(albumLabel, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;

        contentPanel.add(albumField, gbc);

        //-------------------------------------------------
        // ROW 5
        //-------------------------------------------------

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;

        contentPanel.add(trackLabel, gbc);

        gbc.gridx = 1;

        contentPanel.add(trackNumberSpinner, gbc);

        return contentPanel;
    }

    private JPanel createFooter() {

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        footerPanel.setBackground(HEADER_COLOR);

        footerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JButton backButton = createButton("Back");

        JButton uploadButton = createButton("Upload Song");

        //-------------------------------------------------
        // BACK BUTTON
        //-------------------------------------------------

        backButton.addActionListener(e ->
                eventBus.publish(
                        new SwitchCenterScreenEvent(
                                ArtistProfileController.class
                        )
                )
        );

        //-------------------------------------------------
        // UPLOAD BUTTON
        //-------------------------------------------------

        uploadButton.addActionListener(e -> {

            if (uploadController == null) {

                displayError("Upload controller is not connected.");

                return;
            }

            //-------------------------------------------------
            // VALIDATION
            //-------------------------------------------------

            if (getSongTitle().isBlank()) {

                displayError("Please enter a song title.");

                return;
            }

            if (getAlbumName().isBlank()) {

                displayError("Please enter an album name.");

                return;
            }

            //-------------------------------------------------
            // SUBMIT
            //-------------------------------------------------

            uploadController.onSubmitButtonClicked();
        });

        footerPanel.add(backButton);

        footerPanel.add(uploadButton);

        return footerPanel;
    }

    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setForeground(TEXT_COLOR);

        label.setFont(new Font("Arial", Font.PLAIN, 16));

        return label;
    }

    private JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setBackground(BUTTON_COLOR);

        button.setForeground(Color.BLACK);

        button.setFont(new Font("Arial", Font.BOLD, 14));

        button.setFocusPainted(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    //-------------------------------------------------
    // VIEW METHODS
    //-------------------------------------------------

    public void display() {

        setVisible(true);
    }

    public void displaySelectedSongFile(String filePath) {

        selectedSongLabel.setText(filePath);
    }

    public void displaySelectedImageCover(String imagePath) {

        selectedCoverLabel.setText(imagePath);
    }

    public void displayError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    public void displaySuccess(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    //-------------------------------------------------
    // GETTERS
    //-------------------------------------------------

    public String getSongTitle() {

        return titleField.getText().trim();
    }

    public String getAlbumName() {

        return albumField.getText().trim();
    }

    public int getTrackNumber() {

        return (Integer) trackNumberSpinner.getValue();
    }
}