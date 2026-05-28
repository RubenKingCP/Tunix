package tunix.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import tunix.dto.request.AddSongRequest;
import tunix.dto.request.PlaylistCreateRequest;
import tunix.dto.response.AddSongResponse;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.PlaylistResponse;
import tunix.model.ILibraryAsset;
import tunix.model.musicContent.Playlist;
import tunix.service.auth.SessionService;

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

    public ApiResponse<PlaylistResponse> getPlaylistsByName(String query) {
        return apiClient.post("/playlists/name",query,PlaylistResponse.class);
    }

    public List<ILibraryAsset> getUserPlaylists(int longId) {
        ApiResponse<List<PlaylistResponse>> userPlaylists = apiClient.get("/playlists/user/" + longId, new TypeReference<ApiResponse<List<PlaylistResponse>>>() {});
        if (userPlaylists.isSuccess()){
            List<ILibraryAsset> assets = new ArrayList<>();
            for (PlaylistResponse playlist : userPlaylists.getData()) {
                assets.add(new Playlist(playlist.getTitle(), playlist.getId().intValue(), SessionService.Instance.getAccount()));
            }
            return assets;
        }
        
        return new ArrayList<>();
    } 
}
