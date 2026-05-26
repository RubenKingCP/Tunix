package tunixserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(
                new ApiResponse<>(
                        true,
                        "Fetched successfully",
                        artistRequestBackendService.getAllArtistRequests()
                )
        );
    }

    @PutMapping("/{requestId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveArtistRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(
                artistRequestBackendService.approveArtistRequest(requestId)
        );
    }

    @PutMapping("/{requestId}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectArtistRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(
                artistRequestBackendService.rejectArtistRequest(requestId)
        );
    }
}