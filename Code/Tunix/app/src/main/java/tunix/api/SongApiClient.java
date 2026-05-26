package tunix.api;

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

    public ApiResponse<SongResponse> getSongsByName(String query) {
        return apiClient.post("/songs/name",query,SongResponse.class);
    } 
}
