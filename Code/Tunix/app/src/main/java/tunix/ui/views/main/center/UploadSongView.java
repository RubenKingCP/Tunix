package tunix.ui.views.main.center;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tunix.controller.UploadSongController;

public class UploadSongView extends JPanel {

    private JTextField songNameField;

    private JTextField trackNumberField;

    private JLabel selectedSongLabel;

    private JButton chooseSongButton;

    private JButton uploadButton;

    private UploadSongController controller;

    public UploadSongView() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBackground(new Color(18, 18, 18));

        setBorder(
                BorderFactory.createEmptyBorder(
                        24,
                        24,
                        24,
                        24
                )
        );

        add(buildTitle());

        add(Box.createVerticalStrut(24));

        add(buildFilePicker());

        add(Box.createVerticalStrut(24));

        add(buildForm());

        add(Box.createVerticalStrut(24));

        add(buildSubmitButton());
    }

    public void setController(UploadSongController controller) {

        this.controller = controller;
    }

    private JLabel buildTitle() {

        JLabel title = new JLabel("Upload a Song");

        title.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        title.setForeground(Color.WHITE);

        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        return title;
    }

    private JPanel buildFilePicker() {

        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 0, 0)
        );

        panel.setBackground(new Color(18, 18, 18));

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        chooseSongButton = new JButton("Choose Song File");

        chooseSongButton.setBackground(
                new Color(30, 215, 96)
        );

        chooseSongButton.setForeground(Color.BLACK);

        chooseSongButton.setFocusPainted(false);

        chooseSongButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        selectedSongLabel = new JLabel("  No file selected");

        selectedSongLabel.setForeground(Color.LIGHT_GRAY);

        selectedSongLabel.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        chooseSongButton.addActionListener(e -> {

            if (controller != null) {

                controller.onSelectFileClicked();
            }
        });

        panel.add(chooseSongButton);

        panel.add(Box.createHorizontalStrut(12));

        panel.add(selectedSongLabel);

        return panel;
    }

    private JPanel buildForm() {

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBackground(new Color(18, 18, 18));

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints labelConstraints =
                new GridBagConstraints();

        labelConstraints.anchor = GridBagConstraints.WEST;

        labelConstraints.insets =
                new Insets(8, 0, 4, 0);

        labelConstraints.gridx = 0;

        labelConstraints.fill =
                GridBagConstraints.HORIZONTAL;

        labelConstraints.weightx = 1.0;

        GridBagConstraints fieldConstraints =
                new GridBagConstraints();

        fieldConstraints.anchor =
                GridBagConstraints.WEST;

        fieldConstraints.insets =
                new Insets(0, 0, 8, 0);

        fieldConstraints.gridx = 0;

        fieldConstraints.fill =
                GridBagConstraints.HORIZONTAL;

        fieldConstraints.weightx = 1.0;

        //-------------------------------------------------
        // SONG NAME
        //-------------------------------------------------

        labelConstraints.gridy = 0;

        panel.add(
                buildLabel("Song Name"),
                labelConstraints
        );

        songNameField = new JTextField();

        styleTextField(songNameField);

        fieldConstraints.gridy = 1;

        panel.add(songNameField, fieldConstraints);

        //-------------------------------------------------
        // TRACK NUMBER
        //-------------------------------------------------

        labelConstraints.gridy = 2;

        panel.add(
                buildLabel("Track Number"),
                labelConstraints
        );

        trackNumberField = new JTextField();

        styleTextField(trackNumberField);

        fieldConstraints.gridy = 3;

        panel.add(trackNumberField, fieldConstraints);

        return panel;
    }

    private JLabel buildLabel(String text) {

        JLabel label = new JLabel(text);

        label.setForeground(Color.LIGHT_GRAY);

        label.setFont(
                new Font("Arial", Font.PLAIN, 13)
        );

        return label;
    }

    private void styleTextField(JTextField field) {

        field.setBackground(new Color(50, 50, 50));

        field.setForeground(Color.WHITE);

        field.setCaretColor(Color.WHITE);

        field.setFont(
                new Font("Arial", Font.PLAIN, 14)
        );

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(80, 80, 80)
                        ),
                        BorderFactory.createEmptyBorder(
                                6,
                                10,
                                6,
                                10
                        )
                )
        );

        field.setPreferredSize(
                new Dimension(400, 36)
        );
    }

    private JPanel buildSubmitButton() {

        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 0, 0)
        );

        panel.setBackground(new Color(18, 18, 18));

        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        uploadButton = new JButton("Upload Song");

        uploadButton.setBackground(
                new Color(30, 215, 96)
        );

        uploadButton.setForeground(Color.BLACK);

        uploadButton.setFocusPainted(false);

        uploadButton.setFont(
                new Font("Arial", Font.BOLD, 15)
        );

        uploadButton.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        28,
                        10,
                        28
                )
        );

        uploadButton.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        uploadButton.addActionListener(e -> {

            if (controller != null) {

                controller.onSubmitButtonClicked();
            }
        });

        panel.add(uploadButton);

        return panel;
    }

    //-------------------------------------------------
    // VIEW METHODS
    //-------------------------------------------------

    public void displaySelectedSongFile(
            String songFilePath
    ) {

        selectedSongLabel.setText(
                "  " + songFilePath
        );
    }

    public void displaySelectedImageCover(
            String imageFilePath
    ) {

        // intentionally empty
        // use case does not require image upload
    }

    public void display() {

        setVisible(true);
    }

    public void displaySuccess(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Success",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public void displayError(String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    //-------------------------------------------------
    // GETTERS
    //-------------------------------------------------

    public String getSongTitle() {

        return songNameField.getText().trim();
    }

    public int getTrackNumber() {

        try {

            return Integer.parseInt(
                    trackNumberField.getText().trim()
            );

        } catch (Exception e) {

            return 0;
        }
    }

    //-------------------------------------------------
    // STANDALONE TEST
    //-------------------------------------------------

   
}