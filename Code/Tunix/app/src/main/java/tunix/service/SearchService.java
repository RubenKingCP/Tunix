package tunix.service;

import java.util.List;

import tunix.api.AlbumApi;
import tunix.api.PlaylistApiClient;
import tunix.api.SongApiClient;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.SongResponse;
import tunix.model.ILibraryAsset;

public class SearchService {
    private final SongApiClient songApiClient;
    private final PlaylistApiClient playlistApiClient;

    public SearchService(SongApiClient songApiClient, PlaylistApiClient playlistApiClient, AlbumApi albumApi) {
        this.playlistApiClient = playlistApiClient;
        this.songApiClient = songApiClient;
    }

    public List<ILibraryAsset> search(String query, String type) {
        String normalizedType = type == null ? "" : type.trim().toLowerCase();

        if ("song".equals(normalizedType)) {
            ApiResponse<SongResponse> response = songApiClient.getSongsByName(query);
            if (response.isSuccess() && response.getData() != null) {
                return List.of(response.getData().toSong());
            }
            return List.of();
        }

        if ("playlist".equals(normalizedType)) {
            playlistApiClient.getPlaylistsByName(query);
            return List.of();
        }

        if ("album".equals(normalizedType)) {
            return List.of();
        }

        System.err.println("Search for this type has not been implemented yet");
        return List.of();
    }
}
