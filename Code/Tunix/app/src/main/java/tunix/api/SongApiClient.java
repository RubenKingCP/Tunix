package tunix.api;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import tunix.dto.request.SongRequest;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.SongResponse;

public class SongApiClient {
    private final ApiClient apiClient;

    public SongApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResponse<SongResponse> uploadSong(SongRequest songRequest) {
        System.out.println("SongApiClient: Sending request to api client");
        return apiClient.post("/songs/upload", songRequest, SongResponse.class);
    }
    
    public ApiResponse<List<SongResponse>> getSongsByName(String query) {
        System.out.println("SongApiClient: Song search query");
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);

        return apiClient.get(
                "/songs/name?query=" + encodedQuery,
                new TypeReference<ApiResponse<List<SongResponse>>>() {}
        );
    }

    public ApiResponse<List<SongResponse>> getSongs() {
        System.out.println("SongApiClient: Sending request to api client");
        return apiClient.get("/songs/all", new TypeReference<ApiResponse<List<SongResponse>>>() {});
    }

    public ApiResponse<Void> removeSongById(int songId) {

        System.out.println("Removing song with id: " + songId);

        return apiClient.delete(
                "/songs/remove/" + songId,
                new TypeReference<ApiResponse<Void>>() {}
        );
    }
}
