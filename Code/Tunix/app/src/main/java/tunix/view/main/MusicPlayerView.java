package tunix.view.main;

import javax.swing.JPanel;

import tunix.controller.main.MusicPlayerController;

public class MusicPlayerView extends JPanel{
    private MusicPlayerController musicPlayerController;

    public void setController(MusicPlayerController musicPlayerController) {
        this.musicPlayerController = musicPlayerController;
    }
}
