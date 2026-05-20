package tunix.controller;

import tunix.view.centralView.SongView;
import tunix.service.PlaylistService;
import tunix.service.SongService;

public class SongController {
    private final SongService songService;
    private final SongView  songView;
    private final PlaylistService playlistService;

    public SongController(SongService songService, SongView songView, PlaylistService playlistService) {
        this.songService = songService;
        this.songView = songView;
        this.playlistService = playlistService;
    }

    public void addSongToPlaylist(int playlistId, int songId) {
        if(this.playlistService.addSongToPlaylist(playlistId, songId)){
            this.showSuccess("Song added to playlist successfully!");
        } else {
            this.showError("Failed to add song to playlist.");
        }
        
    }

    public void showError(String message) {
        this.songView.showErrorDialog(message);
    }

    public void showSuccess(String message) {
        this.songView.showSuccessDialog(message);
    }
}
