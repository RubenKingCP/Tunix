package tunix.view.centralView;

import tunix.controller.SongController;

public class SongView {
    private final SongController songController;

    public SongView(SongController songController) {
        this.songController = songController;
    }

    public void onSongRightClick() {
        this.showOptionsMenu();
    }

    public void showOptionsMenu() {
        // Gui work to show options menu for the song
    }

    public void onAddToPlaylistClick() {
        this.showSelectPlaylistDialog();
    }

    public void showSelectPlaylistDialog() {
        
    }

    public void onConfirmAddToPlaylist(int playlistId, int songId) {
        this.songController.addSongToPlaylist(playlistId, songId);
    }
}
