package tunix.view.main;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import tunix.controller.main.MusicPlayerController;

public class MusicPlayerView extends JPanel{
    private MusicPlayerController musicPlayerController;

    public MusicPlayerView() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(6,8,6,8));

        JLabel songTitleLabel = new JLabel("Song Title");
        songTitleLabel.setBorder(BorderFactory.createEmptyBorder(0,4,0,4));
        add(songTitleLabel, BorderLayout.WEST);

        // Centered control buttons
        JPanel centerControls = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        JButton shuffleButton = new JButton("Shuffle");
        JButton prevButton = new JButton("Previous");
        JButton playButton = new JButton("Play/Pause");
        JButton nextButton = new JButton("Next");
        shuffleButton.addActionListener(e -> onShuffleButtonClicked());
        prevButton.addActionListener(e -> onPreviousButtonClicked());
        playButton.addActionListener(e -> onPlayPauseButtonClicked());
        nextButton.addActionListener(e -> onNextButtonClicked());
        centerControls.add(shuffleButton);
        centerControls.add(prevButton);
        centerControls.add(playButton);
        centerControls.add(nextButton);
        add(centerControls, BorderLayout.CENTER);
    }

    public void setController(MusicPlayerController musicPlayerController) {
        this.musicPlayerController = musicPlayerController;
    }
    public void onShuffleButtonClicked() {
        musicPlayerController.onShuffleButtonClicked();
    }
    public void onPreviousButtonClicked() {
        musicPlayerController.onPreviousButtonClicked();
    }
    public void onPlayPauseButtonClicked() {
        musicPlayerController.onPlayPauseButtonClicked();
    }
    public void onNextButtonClicked() {
        musicPlayerController.onNextButtonClicked();
    }
}
