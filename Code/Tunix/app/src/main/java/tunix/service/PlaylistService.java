package tunix.service;

import java.util.List;

import tunix.api.PlaylistApiClient;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.PlaylistResponse;
import tunix.model.ILibraryAsset;
import tunix.dto.request.PlaylistCreateRequest;
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

    public boolean createPlaylist(PlaylistCreateRequest playlistRequest, List<ILibraryAsset> libraryAssets) {
        System.out.println("PlaylistService: create Playlist Message send");
        ApiResponse<PlaylistResponse> response = playlistApiClient.createPlaylist(playlistRequest);
        return response.isSuccess();
    }
}