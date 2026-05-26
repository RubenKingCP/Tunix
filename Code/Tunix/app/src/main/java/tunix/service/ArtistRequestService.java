package tunix.service;

import java.util.List;

import tunix.api.ArtistRequestApiClient;
import tunix.dto.enums.ArtistRequestStatus;
import tunix.dto.request.ArtistApplicationRequest;
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
//        var response = artistRequestApiClient.getAllArtistRequests();
//        if (!response.isSuccess()) {
//            throw new RuntimeException(response.getMessage());
//        }
//        List<ArtistRequest> artistRequests = new ArrayList<>();
//        for (ArtistRequestResponse item : response.getData()) {
//            artistRequests.add(toModel(item));
//        }
//        return artistRequests;

    return List.of(
        new ArtistRequest(101, ArtistRequestStatus.Pending, "I want to share my music."),
        new ArtistRequest(102, ArtistRequestStatus.Pending, "Independent producer seeking platform."),
        new ArtistRequest(103, ArtistRequestStatus.Pending, "Singer-songwriter looking to grow audience."),
        new ArtistRequest(104, ArtistRequestStatus.Pending, "DJ wanting to upload original mixes."),
        new ArtistRequest(105, ArtistRequestStatus.Pending, "Band looking to distribute our tracks.")
    );
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

    public void makeRequest(int userId, String stageName, String message){
        artistRequestApiClient.makeRequest(new ArtistApplicationRequest(userId,stageName,message));
    }
}
