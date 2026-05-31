package tunix.controller.main;

import javax.swing.JPanel;

import tunix.model.musicContent.Song;
import tunix.navigation.events.EventBus;
import tunix.navigation.events.SongSelectedEvent;
import tunix.service.MusicPlayerService;
import tunix.ui.views.main.MusicPlayerView;

public class MusicPlayerController {
    private final MusicPlayerService musicPlayerService;
    private final MusicPlayerView musicPlayerView;
    private final EventBus eventBus;
    private java.util.List<Song> currentSongOrder;
    private boolean isShuffled = false;
    private java.util.List<Song> shuffledSongOrder;
    private int currentSongIndex;
    private boolean isPlaying = false;

    public MusicPlayerController(MusicPlayerService musicPlayerService, EventBus eventBus){
        this.musicPlayerService = musicPlayerService;
        this.eventBus = eventBus;
        this.musicPlayerView = new MusicPlayerView();
        this.musicPlayerView.setController(this);
        this.eventBus.subscribe(SongSelectedEvent.class, this::onSongSelected);
    }

    public void onSongSelected(SongSelectedEvent event) {
        // For simplicity, we just take the first song in the order as the current song
        if (event.getSongOrder() != null && !event.getSongOrder().isEmpty()) {
            this.currentSongOrder = event.getSongOrder();
            this.currentSongIndex = 0;
            musicPlayerService.loadSong(currentSongOrder.get(currentSongIndex)); // Load the first song in the order
            musicPlayerView.updateCurrentSong(currentSongOrder.get(currentSongIndex)); // Update the view to show the current song
            // Optionally, you could also update the view to show the current song details
        }
    }

    public JPanel getView() {
        return musicPlayerView;
    }

    public void onNextButtonClicked() {
        System.out.println("Next button clicked");
        if (currentSongIndex < currentSongOrder.size() - 1) {
            currentSongIndex++;
            if (isShuffled) {
                musicPlayerService.loadSong(shuffledSongOrder.get(currentSongIndex)); // Load the next song in the shuffled order
                musicPlayerView.updateCurrentSong(shuffledSongOrder.get(currentSongIndex)); // Update the view to show the current song
                return;
            }
            musicPlayerService.loadSong(currentSongOrder.get(currentSongIndex)); // Load the next song in the order
            musicPlayerView.updateCurrentSong(currentSongOrder.get(currentSongIndex)); // Update the view to show the current song
        } else {
            System.out.println("Already at the end of the song list");
        }
    }

    public void onPlayPauseButtonClicked() {
        System.out.println("Play/Pause button clicked");
        
        System.out.println((isPlaying ? "Playing song: " : "Paused Song: ")+ currentSongOrder.get(currentSongIndex).getTitle());
        isPlaying = !isPlaying;
    }

    public void onPreviousButtonClicked() {
        System.out.println("Previous button clicked");
        if (currentSongIndex > 0){
            currentSongIndex--;
            if (!isShuffled) {
                musicPlayerService.loadSong(currentSongOrder.get(currentSongIndex)); // Load the previous song in the order
                musicPlayerView.updateCurrentSong(currentSongOrder.get(currentSongIndex)); // Update the view to show the current song
            } else {
                musicPlayerService.loadSong(shuffledSongOrder.get(currentSongIndex)); // Load the previous song in the shuffled order
                musicPlayerView.updateCurrentSong(shuffledSongOrder.get(currentSongIndex)); // Update the view to show the current song
            }
        } else {
            System.out.println("Already at the beginning of the song list");
        }
    }

    public void onShuffleButtonClicked() {
        shuffledSongOrder = new java.util.ArrayList<>(currentSongOrder);
        java.util.Collections.shuffle(shuffledSongOrder);
        isShuffled = !isShuffled;
    }
}
