package tunix.view.center;

import tunix.controller.SongController;

public class SongView {
    private final SongController songController;

    public SongView(SongController songController) {
        this.songController = songController;
    }

    public void onSongClicked() {
        songController.playSong(null);
    }

    public void onSongRightClick() {
        this.showOptionsMenu();
    }

    private void showOptionsMenu() {
        // Gui work to show options menu for the song
    }

    public void onAddToPlaylistClick() {
        this.showSelectPlaylistDialog();
    }

    private void showSelectPlaylistDialog() {
        
    }

    public void onConfirmAddToPlaylist(int playlistId, int songId) {
        this.songController.addSongToPlaylist(playlistId, songId);
    }

    public void showErrorDialog(String message) {
        // Logic to show an error dialog to the user
    }

    public void showSuccessDialog(String message) {
        // Logic to show a success dialog to the user
    }
}
