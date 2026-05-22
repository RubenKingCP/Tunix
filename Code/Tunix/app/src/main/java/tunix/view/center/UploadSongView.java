import java.awt.*;
import javax.swing.*;

public class UploadSongView extends JPanel {

    private JTextField songNameField;
    private JTextField albumField;
    private JTextField trackNumberField;

    public UploadSongView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        add(buildTitle());
        add(Box.createVerticalStrut(24));
        add(buildFilePicker());
        add(Box.createVerticalStrut(16));
        add(buildForm());
        add(Box.createVerticalStrut(24));
        add(buildSubmitButton());
    }

    private JLabel buildTitle() {
        JLabel title = new JLabel("Upload a Song");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        return title;
    }
    private JPanel buildFilePicker() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(Color.DARK_GRAY);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton browseButton = new JButton("Choose File");
        browseButton.setBackground(new Color(30, 215, 96)); // Spotify green
        browseButton.setForeground(Color.WHITE);
        browseButton.setFocusPainted(false);
        browseButton.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel fileLabel = new JLabel("  No file selected");
        fileLabel.setForeground(Color.LIGHT_GRAY);
        fileLabel.setFont(new Font("Arial", Font.PLAIN, 13));

        browseButton.addActionListener(e -> {
            FileDialog fileDialog = new FileDialog((Frame) SwingUtilities.getWindowAncestor(this), "Choose Audio File", FileDialog.LOAD);
            fileDialog.setFilenameFilter((dir, name) -> 
                name.endsWith(".mp3") || name.endsWith(".wav") || 
                name.endsWith(".flac") || name.endsWith(".aac")
            );
            fileDialog.setVisible(true);

            String dir = fileDialog.getDirectory();
            String file = fileDialog.getFile();
            if (dir != null && file != null) {
                fileLabel.setText("  " + file);
            }
        });

        panel.add(browseButton);
        panel.add(fileLabel);
        return panel;
    }
    private JPanel buildForm() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.DARK_GRAY);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints labelConstraints = new GridBagConstraints();
        labelConstraints.anchor = GridBagConstraints.WEST;
        labelConstraints.insets = new Insets(8, 0, 4, 0);
        labelConstraints.gridx = 0;
        labelConstraints.fill = GridBagConstraints.HORIZONTAL;
        labelConstraints.weightx = 1.0;

        GridBagConstraints fieldConstraints = new GridBagConstraints();
        fieldConstraints.anchor = GridBagConstraints.WEST;
        fieldConstraints.insets = new Insets(0, 0, 8, 0);
        fieldConstraints.gridx = 0;
        fieldConstraints.fill = GridBagConstraints.HORIZONTAL;
        fieldConstraints.weightx = 1.0;

        // Song Name
        labelConstraints.gridy = 0;
        panel.add(buildLabel("Song Name"), labelConstraints);
        songNameField = new JTextField();
        styleTextField(songNameField);
        fieldConstraints.gridy = 1;
        panel.add(songNameField, fieldConstraints);

        // Album
        labelConstraints.gridy = 2;
        panel.add(buildLabel("Album"), labelConstraints);
        albumField = new JTextField();
        styleTextField(albumField);
        fieldConstraints.gridy = 3;
        panel.add(albumField, fieldConstraints);

        // Track Number
        labelConstraints.gridy = 4;
        panel.add(buildLabel("Track Number"), labelConstraints);
        trackNumberField = new JTextField();
        styleTextField(trackNumberField);
        fieldConstraints.gridy = 5;
        panel.add(trackNumberField, fieldConstraints);

        return panel;
    }

    private JLabel buildLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(80, 80, 80)),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        field.setPreferredSize(new Dimension(400, 36));
    }
    private JPanel buildSubmitButton() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setBackground(Color.DARK_GRAY);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton submitButton = new JButton("Upload Song");
        submitButton.setBackground(new Color(30, 215, 96));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setFont(new Font("Arial", Font.BOLD, 15));
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 28, 10, 28));
        submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        submitButton.addActionListener(e -> {
            String songName = songNameField.getText().trim();
            String album = albumField.getText().trim();
            String trackNumber = trackNumberField.getText().trim();

            if (songName.isEmpty() || album.isEmpty() || trackNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please fill in all fields and select a file.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // TODO: wire up to controller
            JOptionPane.showMessageDialog(this,
                "Song uploaded successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(submitButton);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tunix - Upload Song");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new UploadSongView());
            frame.setSize(600, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
