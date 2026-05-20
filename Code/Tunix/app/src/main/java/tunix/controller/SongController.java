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
        this.playlistService.addSongToPlaylist(playlistId, songId);
    }
}
