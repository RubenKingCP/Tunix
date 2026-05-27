package tunix.api;

import tunix.dto.request.ArtistApplicationRequest;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.ArtistApplicationResponse;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;



public class ArtistRequestApiClient {

    private final ApiClient apiClient;

    public ArtistRequestApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiResponse<List<ArtistApplicationResponse>> getAllArtistRequests() {
        System.out.println("ArtistRequestApiClient: Sending fetch requests");
        return apiClient.get(
                "/artist-requests/getAll",
                new TypeReference<ApiResponse<List<ArtistApplicationResponse>>>() {}
        );
    }

    public ApiResponse<ArtistApplicationResponse> approveArtistRequest(int requestId) {
        System.out.print("ArtistBackendController: Approve request with id: " + requestId);
        return apiClient.put(
                "/artist-requests/" + requestId + "/approve",
                "",
                ArtistApplicationResponse.class
        );
    }

    public ApiResponse<ArtistApplicationResponse> rejectArtistRequest(int requestId) {
        return apiClient.post(
                "/artist-requests/" + requestId + "/reject",
                "",
                ArtistApplicationResponse.class
        );
    }

    public ApiResponse<ArtistApplicationResponse> makeRequest(ArtistApplicationRequest req) {
        System.out.println("ArtistRequestApiClient: Check for data existence:" + req.getUserId() + '\n' + req.getStageName() + '\n' + req.getReason());
        return apiClient.post(
                "/artist-requests/make",
                req,
                ArtistApplicationResponse.class
        );
    }
}
