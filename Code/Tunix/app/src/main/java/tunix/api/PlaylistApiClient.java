package tunix.api;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import tunix.dto.request.AddSongRequest;
import tunix.dto.request.PlaylistCreateRequest;
import tunix.dto.response.AddSongResponse;
import tunix.dto.response.AlbumResponse;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.PlaylistResponse;
import tunix.dto.response.LibraryResponse;
import tunix.dto.response.SongResponse;
import tunix.model.ILibraryAsset;
import tunix.model.musicContent.Album;
import tunix.model.musicContent.Playlist;
import tunix.service.auth.SessionService;

public class PlaylistApiClient {
    private final ApiClient apiClient;

    public PlaylistApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResponse<AddSongResponse> addSongToPlaylist(int playlistId, int songId) {
        // Logic to call the API to add the song to the playlist
        // Backend exposes: POST /playlists/{playlistId}/add/{songId}
        return apiClient.post("/playlists/" + playlistId + "/add/" + songId, null, AddSongResponse.class);
    }

    public ApiResponse<PlaylistResponse> createPlaylist(PlaylistCreateRequest playlistCreateRequest) {
        return apiClient.post("/playlists/create", playlistCreateRequest, PlaylistResponse.class);
    }

    public ApiResponse<List<PlaylistResponse>> getPlaylistsByName(String query) {
        System.out.println("PlaylistApi: Reached the get albums by name query: " + query);
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        return apiClient.get("/playlists/name?query=" + encodedQuery, new TypeReference<ApiResponse<List<PlaylistResponse>>>(){});
    }

    public List<ILibraryAsset> getUserPlaylists(int longId) {
        // The backend exposes user playlists as part of the user's library: /library/{accountId}
        ApiResponse<LibraryResponse> resp = apiClient.get("/library/" + longId, new TypeReference<ApiResponse<LibraryResponse>>(){});

        List<ILibraryAsset> assets = new ArrayList<>();

        if (resp != null && resp.isSuccess() && resp.getData() != null && resp.getData().getPlaylists() != null) {
            for (PlaylistResponse playlist : resp.getData().getPlaylists()) {
                assets.add(new Playlist(playlist.getTitle(), playlist.getId().intValue(), SessionService.Instance.getAccount()));
            }
        }

        return assets;
    }

    public ILibraryAsset getById(long id) {

        ApiResponse<PlaylistResponse> response = apiClient.get(
                "/playlists/" + id,
                new TypeReference<ApiResponse<PlaylistResponse>>() {}
        );

        if (response == null || !response.isSuccess() || response.getData() == null) {
            return null;
        }

        PlaylistResponse dto = response.getData();

        Playlist playlist = new Playlist(
                dto.getTitle(),
                dto.getId().intValue(),
                SessionService.Instance.getAccount()
        );

        // visibility
        if (dto.isPublic()) {
            playlist.toggleVisibility();
        }

        // songs
        if (dto.getSongs() != null) {
            dto.getSongs().stream()
                    .map(SongResponse::toSong)
                    .forEach(playlist::addSong);
        }

        return playlist;
    }
}
    
