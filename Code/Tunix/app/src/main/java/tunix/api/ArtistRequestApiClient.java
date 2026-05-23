package tunix.api;

import tunix.dto.response.ApiResponse;
import tunix.dto.response.ArtistRequestResponse;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;



public class ArtistRequestApiClient {
    private final ApiClient apiClient;

    public ArtistRequestApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }
    public ApiResponse<List<ArtistRequestResponse>> getAllArtistRequests() {
        // Code to make API call to backend and retrieve artist requests

            return apiClient.get("/artist-requests", new TypeReference<ApiResponse<List<ArtistRequestResponse>>>() {});
    }

    public ApiResponse<ArtistRequestResponse> approveArtistRequest(int requestId) {
        // Code to make API call to backend to approve artist request
        return apiClient.post("/artist-requests/approve", requestId, ArtistRequestResponse.class);
        
    }

    public ApiResponse<ArtistRequestResponse> rejectArtistRequest(int requestId) {
        // Code to make API call to backend to reject artist request
        return apiClient.post("/artist-requests/reject", requestId, ArtistRequestResponse.class);
    }
}
