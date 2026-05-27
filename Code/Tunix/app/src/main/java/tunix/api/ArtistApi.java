package tunix.api;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;

import tunix.dto.response.*;

public class ArtistApi {
    private final ApiClient apiClient;
    public ArtistApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    public ApiResponse<List<ArtistResponse>> searchArtistsByName(String query) {
        throw new UnsupportedOperationException("Not implemented yet");
        //return apiClient.get("/artists/name", query,
        //                         new TypeReference<ApiResponse<List<ArtistResponse>>>() {});
    }
}
