package tunix.service;

import java.util.List;

import tunix.api.ArtistRequestApiClient;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.ArtistRequestResponse;
import tunix.model.ArtistRequest;
import java.util.ArrayList;

public class ArtistRequestService {
    private final ArtistRequestApiClient artistRequestApiClient;
    public ArtistRequestService(ArtistRequestApiClient artistRequestApiClient) {
        this.artistRequestApiClient = artistRequestApiClient;
    }

    
    public List<ArtistRequest> getArtistRequests() {

        var response = artistRequestApiClient.getAllArtistRequests();

        if (!response.isSuccess()) {
            throw new RuntimeException(response.getMessage());
        }

        List<ArtistRequest> artistRequests = new ArrayList<>();

        for (ArtistRequestResponse item : response.getData()) {
            artistRequests.add(toModel(item));
        }

        return artistRequests;
   } 

    public ArtistRequest toModel(ArtistRequestResponse dto) {

        return new ArtistRequest(0, null, null);
    }

    public boolean approveArtistRequest(int requestId) {
        // Code to approve artist request via API call
        ApiResponse<ArtistRequestResponse> response = artistRequestApiClient.approveArtistRequest(requestId);
        // Handle error success
        return true;
    }

    public boolean rejectArtistRequest(int requestId) {
        // Code to reject artist request via API call
        ApiResponse<ArtistRequestResponse> response = artistRequestApiClient.approveArtistRequest(requestId);
        // Handle error success
        return true;
    }
}
