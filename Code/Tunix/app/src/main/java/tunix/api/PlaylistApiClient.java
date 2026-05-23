package tunix.api;

import tunix.dto.request.AddSongRequest;
import tunix.dto.request.PlaylistCreateRequest;
import tunix.dto.response.AddSongResponse;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.PlaylistResponse;

public class PlaylistApiClient {
    private final ApiClient apiClient;

    public PlaylistApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResponse<AddSongResponse> addSongToPlaylist(int playlistId, int songId) {
        // Logic to call the API to add the song to the playlist
        return apiClient.post("/playlists/" + playlistId + "/add", new AddSongRequest(songId), AddSongResponse.class);
    }

    public ApiResponse<PlaylistResponse> createPlaylist(PlaylistCreateRequest playlistCreateRequest) {
        return apiClient.post("/playlists/create", playlistCreateRequest, PlaylistResponse.class);
    } 
}
