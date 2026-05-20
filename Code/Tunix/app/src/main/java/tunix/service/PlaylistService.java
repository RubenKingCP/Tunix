package tunix.service;

import tunix.api.PlaylistApiClient;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.AddSongResponse;

public class PlaylistService {
    private final PlaylistApiClient playlistApiClient;


    public PlaylistService(PlaylistApiClient playlistApiClient) {
        this.playlistApiClient = playlistApiClient;
    }
    public ApiResponse<AddSongResponse> addSongToPlaylist(int playlistId, int songId) {
        // Logic to add the song to the playlist in the database
        return playlistApiClient.addSongToPlaylist(playlistId, songId);
    }
    
}
