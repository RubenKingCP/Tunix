package tunix.service;

import java.util.List;

import tunix.api.PlaylistApiClient;
import tunix.dto.response.ApiResponse;
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
        if (!checkDuplicateName(playlistRequest, libraryAssets)){
            playlistApiClient.createPlaylist(playlistRequest);
            return true;
        } else return false;
    }

    public boolean checkDuplicateName(PlaylistCreateRequest playlistRequest, List<ILibraryAsset> libraryAssets) {
        // Placeholder for check
        return false;
    }
}