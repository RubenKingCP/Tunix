package main.java.tunix.view;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;

import java.awt.*;
import java.awt.image.*;

public class SongView extends JFrame{
    // CLASS DIAGRAM CLASSES
    public void onRightClickSong() {}
    private void showOptionsMenu() {}
    public void onAddToPlaylistClicked() {}
    private void showPlaylistSelectionDialog() {}
    public void onConfirmAddToPlaylistClicked(int playlistId, int songId) {}
    public void showExtraMenu() {} 
    public void showErrorDialog(String message) {}
    public void showSuccessDialog(String message) {}

    // FRONTEND STUFF
    private JPanel sidebar;    // null for now — added later
    private JPanel playerPanel;

    public SongView() {
        setTitle("Tunix");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        playerPanel = buildPlayerPanel();
        add(playerPanel, BorderLayout.CENTER);
        // later: add(sidebar, BorderLayout.WEST);

        pack();
        setMinimumSize(new Dimension(400, 550));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildPlayerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.DARK_GRAY); // temp background so we can see the panel
        panel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        panel.add(Box.createVerticalGlue());
        panel.add(buildAlbumCover());
        panel.add(buildSongNameRow());
        panel.add(buildProgressSlider());
        panel.add(buildControls());
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JLabel buildAlbumCover() {
        // Placeholder: a solid gray square where the album art will go
        int SIZE = 300;

        BufferedImage placeholder = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = placeholder.createGraphics();
        g2.setColor(new Color(0x3A3A3A));
        g2.fillRect(0, 0, SIZE, SIZE);
        g2.setColor(new Color(0x606060));
        g2.setFont(new Font("Dialog", Font.PLAIN, 48));
        FontMetrics fm = g2.getFontMetrics();
        String note = "♪";
        g2.drawString(note, (SIZE - fm.stringWidth(note)) / 2, (SIZE + fm.getAscent()) / 2);
        g2.dispose();

        JLabel cover = new JLabel(new ImageIcon(placeholder));
        cover.setAlignmentX(Component.CENTER_ALIGNMENT); // centers it inside the BoxLayout
        return cover;
    }

    private JPanel buildSongNameRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        row.setBackground(Color.DARK_GRAY);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel songName = new JLabel("Song Title Placeholder");
        songName.setForeground(Color.WHITE);
        songName.setFont(new Font("Dialog", Font.BOLD, 18));

        JButton optionsButton = new JButton("⋯");
        optionsButton.setFocusPainted(false);
        optionsButton.setBorderPainted(false);
        optionsButton.setContentAreaFilled(false);
        optionsButton.setForeground(Color.LIGHT_GRAY);
        optionsButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        optionsButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        row.add(songName);
        row.add(optionsButton);
        return row;
    }

    private JSlider buildProgressSlider() {
        JSlider slider = new JSlider(0, 100, 30);
        slider.setBackground(Color.DARK_GRAY);
        slider.setPaintTicks(false);
        slider.setPaintLabels(false);
        slider.setMaximumSize(new Dimension(280, 40));
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);

        slider.setUI(new BasicSliderUI(slider) {
            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);

                int size = 14; // fixed diameter
                int x = thumbRect.x + (thumbRect.width - size) / 2;
                int y = thumbRect.y + (thumbRect.height - size) / 2;
                g2.fillOval(x, y, size, size);
            }

            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(new Color(0x555555));
                g2.fillRect(trackRect.x, trackRect.y + (trackRect.height / 2) - 2, trackRect.width, 4);
            }
        });

        slider.addChangeListener(e -> slider.repaint());

        return slider;
    }

    private JPanel buildControls() {
    JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 8));
    row.setBackground(Color.DARK_GRAY);
    row.setAlignmentX(Component.CENTER_ALIGNMENT);

    JButton rewindButton = buildControlButton("−5s");
    JButton playPauseButton = buildControlButton("▶");
    JButton forwardButton = buildControlButton("+5s");

    // placeholder toggle — no backend yet, just so the button feels alive
    playPauseButton.addActionListener(e -> {
        boolean isPlaying = playPauseButton.getText().equals("▶");
        playPauseButton.setText(isPlaying ? "⏸" : "▶");
    });

    row.add(rewindButton);
    row.add(playPauseButton);
    row.add(forwardButton);
    return row;
    }

    private JButton buildControlButton(String label) {
        JButton button = new JButton(label);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Dialog", Font.BOLD, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }

    // temp for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SongView());
    }
}
