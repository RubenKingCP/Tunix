package tunix.service;

import tunix.api.PlaylistApiClient;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.AddSongResponse;

public class PlaylistService {

    private final PlaylistApiClient playlistApiClient;

    public PlaylistService(PlaylistApiClient playlistApiClient) {
        this.playlistApiClient = playlistApiClient;
    }

    public boolean addSongToPlaylist(int playlistId, int songId) {

        ApiResponse<AddSongResponse> response =
                playlistApiClient.addSongToPlaylist(
                        playlistId,
                        songId
                );

        if (response.isSuccess()) {

            // optional extra frontend logic

            

        } else {

            System.out.println(
                    "Failed: " + response.getMessage()
            );
        }

        return response.isSuccess();
    }
}