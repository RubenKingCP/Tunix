package tunix.service;

import java.util.List;

import tunix.api.ArtistRequestApiClient;
import tunix.dto.enums.ArtistRequestStatus;
import tunix.dto.request.ArtistApplicationRequest;
import tunix.dto.response.ApiResponse;
import tunix.dto.response.ArtistApplicationResponse;
import tunix.model.ArtistRequest;

public class ArtistRequestService {
    private final ArtistRequestApiClient artistRequestApiClient;
    public ArtistRequestService(ArtistRequestApiClient artistRequestApiClient) {
        this.artistRequestApiClient = artistRequestApiClient;
    }

    
    public List<ArtistRequest> getArtistRequests() {

        System.err.println("Reached ArtistRequestService for all artist requests");

        try {
            var response = artistRequestApiClient.getAllArtistRequests();

            System.err.println("ArtistRequestService: response received");
            System.err.println("SUCCESS FLAG = " + response.isSuccess());

            if (!response.isSuccess()) {
                System.err.println("Backend returned failure: " + response.getMessage());
                return mockArtistRequests();
            }

            if (response.getData() == null) {
                System.err.println("DATA is null");
                return mockArtistRequests();
            }

            System.err.println("DATA SIZE = " + response.getData().size());
            response.getData().forEach(d ->
            System.err.println("STATUS FROM API = " + d.getStatus())
        );
            return response.getData()
                    .stream()
                    .map(this::toModel)
                    .toList(); 

        } catch (Exception e) {
            System.err.println("API FAILED FULL STACKTRACE:");
            e.printStackTrace();
        }

        return mockArtistRequests();
    }

    private List<ArtistRequest> mockArtistRequests() {
    return List.of(
        new ArtistRequest(101, "Mpamphs", ArtistRequestStatus.Pending, "I want to share my music."),
        new ArtistRequest(102, "Marianna", ArtistRequestStatus.Pending, "Independent producer seeking platform."),
        new ArtistRequest(103, "Kostas", ArtistRequestStatus.Pending, "Singer-songwriter looking to grow audience."),
        new ArtistRequest(104, "Eirhrnh", ArtistRequestStatus.Pending, "DJ wanting to upload original mixes."),
        new ArtistRequest(105, "Pavlos", ArtistRequestStatus.Pending, "Band looking to distribute our tracks.")
    );
}

    private ArtistRequest toModel(ArtistApplicationResponse dto) {

        ArtistRequestStatus status;

        try {
                    switch (dto.getStatus().trim().toUpperCase()) {
            case "APPROVED":
                status = ArtistRequestStatus.Approved;
                break;
            case "REJECTED":
                status = ArtistRequestStatus.Rejected;
                break;
            case "PENDING":
                status = ArtistRequestStatus.Pending;
                break;
            default:
                status = ArtistRequestStatus.Pending;
}
        } catch (Exception e) {
            System.err.println("Invalid status from API: " + dto.getStatus());
            status = ArtistRequestStatus.Pending; // safe fallback
        }

        return new ArtistRequest(
            dto.getApplicantId().intValue(),
            dto.getStageName(),   // or requestId depending on your model
            status,
            dto.getReason()
        );
    }

    public boolean approveArtistRequest(int requestId) {
        // Code to approve artist request via API call
        ApiResponse<ArtistApplicationResponse> response = artistRequestApiClient.approveArtistRequest(requestId);
        // Handle error success
        return response.isSuccess();
    }

    public boolean rejectArtistRequest(int requestId) {
        // Code to reject artist request via API call
        ApiResponse<ArtistApplicationResponse> response = artistRequestApiClient.approveArtistRequest(requestId);
        // Handle error success
        return response.isSuccess();
    }

    public void makeRequest(Long userId, String stageName, String message){
        System.out.println("ArtistRequestService: Creating artist request for: \nuserId: " + userId + "\nStage Name: " + stageName + "\nReason: " + message);
        artistRequestApiClient.makeRequest(new ArtistApplicationRequest(userId,stageName,message));
    }
}
