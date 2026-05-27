package tunixserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tunixserver.dto.request.ArtistApplicationRequest;
import tunixserver.dto.response.ApiResponse;
import tunixserver.dto.response.ArtistRequestResponse;
import tunixserver.service.ArtistRequestBackendService;

import java.util.List;


@RestController
@RequestMapping("/artist-requests")
public class ArtistRequestBackendController {

    private final ArtistRequestBackendService artistRequestBackendService;

    public ArtistRequestBackendController(ArtistRequestBackendService artistRequestBackendService) {
        this.artistRequestBackendService = artistRequestBackendService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ArtistRequestResponse>>> getAllArtistRequests() {

        System.out.println("ArtistRequestBackend: Getting all artist requests");

        try {
            List<ArtistRequestResponse> requests =
                    artistRequestBackendService.getAllArtistRequests();

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            true,
                            "Fetched successfully",
                            requests
                    )
            );

        } catch (Exception e) {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            false,
                            "Failed to fetch requests: " + e.getMessage(),
                            null
                    )
            );
        }
    }

    @PutMapping("/{requestId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveArtistRequest(@PathVariable Long requestId) {

        System.out.println("ArtistRequestBackend: Approving request " + requestId);

        try {
            ApiResponse<Void> response =
                    artistRequestBackendService.approveArtistRequest(requestId);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            false,
                            "Failed to approve request: " + e.getMessage(),
                            null
                    )
            );
        }
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectArtistRequest(@PathVariable Long requestId) {

        System.out.println("ArtistRequestBackend: Rejecting request " + requestId);

        try {
            ApiResponse<Void> response =
                    artistRequestBackendService.rejectArtistRequest(requestId);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            false,
                            "Failed to reject request: " + e.getMessage(),
                            null
                    )
            );
        }
    }
    
    @PostMapping("/make")
    public ResponseEntity<ApiResponse<Void>> createArtistRequest(
            @RequestBody ArtistApplicationRequest dto) {

        System.out.println("ArtistRequestBackend: Creating artist request for user " + dto.getUserId());
        System.out.println("Get request data for check: \n" + dto.getStageName() + "\n" + dto.getReason() + '\n' + dto.getUserId());
        try {
            ApiResponse<Void> response =
                    artistRequestBackendService.createArtistRequest(dto);

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity.ok(
                    new ApiResponse<>(
                            false,
                            "Failed to create request: " + e.getMessage(),
                            null
                    )
            );
        }
    }
}